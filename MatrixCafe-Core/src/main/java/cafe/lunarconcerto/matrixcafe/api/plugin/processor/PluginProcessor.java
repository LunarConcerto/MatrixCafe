package cafe.lunarconcerto.matrixcafe.api.plugin.processor;

import cafe.lunarconcerto.matrixcafe.api.plugin.annotations.PluginInformation;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PackageContents;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginInfo;
import cafe.lunarconcerto.matrixcafe.api.util.Json;
import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.aptk.tools.generators.SimpleResourceWriter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.*;

/**
 * 插件主类注解处理器
 * @see PluginInformation
 */
public class PluginProcessor extends AbstractAnnotationProcessor {

    public static final String PLUGIN_RESOURCE = "META-INF/plugin-info.json";

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest() ;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(PluginInformation.class.getName());
    }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, @NotNull RoundEnvironment roundEnv) {
        Set<? extends Element> pluginInfoClasses = roundEnv.getElementsAnnotatedWith(PluginInformation.class);
        if (pluginInfoClasses.isEmpty()){
            return false ;
        }

        List<PluginInfo> pluginInfos = new LinkedList<>();
        pluginInfoClasses.stream()
                .filter(annotatedClass -> annotatedClass instanceof TypeElement)
                .forEachOrdered(annotatedClass -> {
            String className = ((TypeElement) annotatedClass).getQualifiedName().toString();
            info("获取到插件主类 -> " + className);

            pluginInfos.add(PluginInfo.fromAnnotation(
                    annotatedClass.getAnnotation(PluginInformation.class),
                    className
            ));

        });


        String json = Json.write(new PackageContents(pluginInfos));

        try {
            FileObject fileObject = createResourceFileObject();

            SimpleResourceWriter writer = new SimpleResourceWriter(fileObject);
            writer.write(json);
            writer.close();
            return true ;
        } catch (IOException e) {
            return false;
        }
    }

    private void info(String info){
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, info);
    }

    private FileObject createResourceFileObject() throws IOException {
        return this.processingEnv.getFiler()
                .createResource(StandardLocation.CLASS_OUTPUT, "", PLUGIN_RESOURCE);
    }

}
