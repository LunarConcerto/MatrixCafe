package cafe.lunarconcerto.matrixcafe.api.common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

/**
 * 规定 MatrixCafe 中的各类路径
 */
public enum DirectoryConstants {

    MAIN_DIR(""),

    CONFIG_DIR("config"),

    PLUGIN_DIR("plugin"),

    CACHE_DIR("cache"),

    PLUGIN_LIB("plugin_lib"),

    ;

    private final String path;

    private final File file;

    DirectoryConstants(String path) {
        this.path = path;
        file = new File(path);
        // 调用对应路径时即创建对应的文件夹
        create();
    }

    /**
     * 返回该路径的相对路径
     *
     * @return 在最后位带"/"的字符串
     */
    public @NotNull String get() {
        return path + "/";
    }

    public String path(){
        return path ;
    }

    public @NotNull Path toPath(){
        return file.toPath();
    }

    /**
     * 返回 该路径 + "/" + 传入路径的文件。
     * @param name 需要在该路径上相对指向的路径
     * @return File 对象
     */
    @Contract(pure = true)
    public @NotNull File of(String name) {
        return new File(get() + name);
    }

    public @NotNull String absolute() {
        return file.getAbsolutePath();
    }

    @Contract(" -> new")
    public @NotNull File file() {
        return file;
    }

    public void create(){
        if (!file.exists()) {
            file.mkdir();
        }
    }

}
