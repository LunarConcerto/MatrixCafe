package cafe.lunarconcerto.matrixcafe.api.plugin;

import cafe.lunarconcerto.matrixcafe.api.util.Bytes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 插件类加载器
 * <p>
 * 如果是 jar 插件, 则该类加载器加载整个 jar. 如果是 class 插件, 则该类加载器加载单个类.
 */
public class PluginClassLoader extends URLClassLoader {

    /**
     * 创建一个新的实例用于读取 .class 文件.
     * @param parent 任意 {@link ClassLoader} , 用以作为该 ClassLoader 的父项
     * @param firstFile 要读取的文件, 此处应输入 .class 类型的文件
     * @return 一个读取了输入的文件的新 {@link PluginClassLoader} 实例
     */
    public static @NotNull PluginClassLoader classLoader(ClassLoader parent, File firstFile) throws IOException {
        return new PluginClassLoader(fileUrl(firstFile), parent);
    }

    /**
     * 创建一个新的实例用于读取 .jar 文件.
     * @param parent 任意 {@link ClassLoader} , 用以作为该 ClassLoader 的父项
     * @param firstFile 要读取的文件, 此处应输入 .jar 类型的文件
     * @return 一个读取了输入的文件的新 {@link PluginClassLoader} 实例
     */
    public static @NotNull PluginClassLoader jarLoader(ClassLoader parent, File firstFile) throws IOException {
        return new PluginClassLoader(jarUrl(firstFile), parent);
    }

    public PluginClassLoader(URL url, ClassLoader parent) {
        super(new URL[]{url}, parent);
    }

    public PluginClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    public void addFile(@NotNull File file){
        try {
            addURL(file.getCanonicalFile().toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addJarFile(@NotNull File file){
        try {
            addURL(jarUrl(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> loadClassFromFile(File file) throws IOException {
        byte[] bytes = Bytes.readFrom(file);
        return defineClass(null, bytes, 0, bytes.length) ;
    }


    @Contract("_ -> new")
    public static @NotNull URL jarUrl(File file) throws IOException {
        return new URL("jar:" + fileUrl(file) + "!/") ;
    }

    @Contract("_ -> new")
    public static @NotNull URL fileUrl(@NotNull File file) throws IOException {
        return file.getCanonicalFile().toURI().toURL() ;
    }

}
