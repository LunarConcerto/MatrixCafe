package cafe.lunarconcerto.matrixcafe.api.plugin;

import cafe.lunarconcerto.matrixcafe.api.application.DirectoryConstants;
import cafe.lunarconcerto.matrixcafe.api.util.Lists;
import lombok.Getter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询 {@link PluginFileFinder#pluginDirectory} 目录下可被加载的 Plugin 文件 ,
 * 一般是 .jar 或 .class ;
 */
@Getter
public class PluginFileFinder {

    protected static final PluginFileFinder DEFAULT_FILE_FINDER = new PluginFileFinder(DirectoryConstants.PLUGIN_DIR.get());

    protected String pluginDirectory ;

    protected List<File> jarFiles ;

    protected List<File> classFiles ;

    public PluginFileFinder(String pluginDirectory) {
        this.pluginDirectory = pluginDirectory;
    }

    /**
     * 查询插件文件,
     * 在查询完成后会记录在当前对象中.
     */
    public void find(){
        File file = new File(pluginDirectory);
        if (!file.exists() || file.isFile()) {
            return ;
        }

        File[] files = file.listFiles();
        if (files == null || files.length <= 0) {
            return ;
        }

        List<File> jarFiles = find(files, ".jar");
        List<File> classFiles = find(files, ".class");

        this.jarFiles = jarFiles;
        this.classFiles = classFiles;
    }

    protected List<File> find(File[] files, String suffix){
        return Arrays.stream(files)
                .filter(checkFile -> checkFile.getName().endsWith(suffix))
                .collect(Collectors.toList());
    }

    public boolean hasJarFiles(){
        return Lists.isValidList(jarFiles) ;
    }

    public boolean hasClassFiles(){
        return Lists.isValidList(classFiles);
    }

    public boolean hasAvailableFiles(){
        return hasJarFiles() || hasClassFiles() ;
    }

}
