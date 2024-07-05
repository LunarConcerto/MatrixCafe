package cafe.lunarconcerto.matrixcafe.api.plugin.mvn;

import cafe.lunarconcerto.matrixcafe.api.application.DirectoryConstants;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginClassContainer;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositoryListener;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transfer.TransferListener;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.filter.DependencyFilterUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

/**
 * 插件的工件依赖解析器
 */
@Slf4j
public class ArtifactDependencyResolver {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Static
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Maven 工件仓库源链接
     */
    private static final List<String> repositoryList = Arrays.asList(
            /* 阿里云镜像 */
            "https://maven.aliyun.com/repository/public/",
            /* 腾讯云镜像 */
            "https://mirrors.cloud.tencent.com/repository/maven/",
            /* Maven 中央仓库 */
            "https://repo1.maven.org/maven2/"
    );

    /**
     * 添加工件仓库源
     * @param url 仓库源链接
     */
    public static void addRepositoryUrl(String url) {
        repositoryList.add(0, url);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected RepositorySystem repositorySystem ;

    protected DependencyFilter classpathFilter = DependencyFilterUtils.classpathFilter(JavaScopes.RUNTIME);

    protected LocalRepository localRepository ;

    protected TransferListener transferListener ;

    protected RepositoryListener repositoryListener ;

    protected List<RemoteRepository> remoteRepositories ;

    protected List<RequirementDependencyPluginWrapper> requirementDependencyPluginWrappers ;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public ArtifactDependencyResolver(Map<String, PluginClassContainer> existPluginMap) {
        requirementDependencyPluginWrappers = new LinkedList<>();
        for (PluginClassContainer value : existPluginMap.values()) {
            String[] libraries = value.getLibraries();
            if (libraries != Strings.EMPTY_ARRAY){
                requirementDependencyPluginWrappers.add(new RequirementDependencyPluginWrapper(value));
            }
        }

        if (!requirementDependencyPluginWrappers.isEmpty()){
            initMavenResolver();
        }
    }

    protected void initMavenResolver(){
        repositorySystem = newRepositorySystem();
        remoteRepositories = createRepositories();
        localRepository = new LocalRepository(DirectoryConstants.PLUGIN_LIB.path());
        transferListener = new ConsoleTransferListener();
        repositoryListener = new LogbackRepositoryListener();
    }

    /**
     * 解析工件描述符列表
     */
    public void resolve() throws Exception {
        for (RequirementDependencyPluginWrapper wrapper : requirementDependencyPluginWrappers) {
            resolve(wrapper);
        }
    }

    protected void resolve(@NotNull RequirementDependencyPluginWrapper wrapper) throws DependencyResolutionException {
        for (String artifactStatement : wrapper.getLibraries()) {
            RepositorySystemSession session = newRepositorySystemSession(repositorySystem);

            Artifact artifact = new DefaultArtifact(artifactStatement);

            CollectRequest collectRequest = new CollectRequest();
            collectRequest.setRoot(new Dependency(artifact, JavaScopes.RUNTIME));
            collectRequest.setRepositories(remoteRepositories);

            DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, classpathFilter);

            for (ArtifactResult artifactResult : repositorySystem.resolveDependencies(session, dependencyRequest).getArtifactResults()) {
                wrapper.add(artifactResult.getArtifact().getFile());
            }
        }

    }

    /**
     * 将已解析的工件加载到类路径中.
     * @throws IOException
     */
    public void load() throws IOException {
        for (RequirementDependencyPluginWrapper wrapper : requirementDependencyPluginWrappers) {
            wrapper.load();
        }
    }

    protected @NotNull DefaultRepositorySystemSession newRepositorySystemSession(@NotNull RepositorySystem system) {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepository));
        session.setTransferListener(transferListener);
        session.setRepositoryListener(repositoryListener);
        return session;
    }

    protected static @NotNull List<RemoteRepository> createRepositories() {
        List<RemoteRepository> result = new ArrayList<>(repositoryList.size());
        for (int i = 0, repositoryListSize = repositoryList.size(); i < repositoryListSize; i++) {
            String repoUrl = repositoryList.get(i);
            RemoteRepository repository = new RemoteRepository.Builder("central" + i , "default", repoUrl).build();
            result.add(repository);
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    protected static RepositorySystem newRepositorySystem() {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        locator.addService(TransporterFactory.class, FileTransporterFactory.class);
        locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
        return locator.getService(RepositorySystem.class);
    }

}
