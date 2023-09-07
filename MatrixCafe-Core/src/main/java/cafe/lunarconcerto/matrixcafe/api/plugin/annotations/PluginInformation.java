package cafe.lunarconcerto.matrixcafe.api.plugin.annotations;

import cafe.lunarconcerto.matrixcafe.api.plugin.Plugin;
import cafe.lunarconcerto.matrixcafe.api.plugin.PluginException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解可以标注在任意类或是继承了{@link Plugin}的类的类名上，
 * 此举将使被标注的类记录为插件主类，并会在编译期通过注解处理器生成索引文件,
 * 故若没有使用该注解标注的插件, 可能无法被识别以及读取.
 * <p>
 * 另一种方法是用户可以手动编写索引文件, 一个标准的索引文件在本模块中带有示例.
 * <p>
 * 该注解将被保留到运行时. 这是为了兼容纯class类插件,
 * 当插件为 jar 插件时, 将只检查 META-INF/plugin-info.json 文件, 这是因为遍历 jar 文件来找到插件主类的话太浪费性能.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginInformation {

    /**
     * 声明该插件的ID
     * <p>
     * ID是用来索引该插件实例的凭据,
     * 若在运行时读取到重复的插件id,
     * 将会抛出 {@link PluginException} 异常.
     * @return 非空, 若为空, 程序将抛出异常.
     */
    String id();

    /**
     * 声明该插件的版本号.
     * <p>
     * MatrixCafe 使用语义化版本控制 (Semantic Versioning) 来检查插件的版本依赖关系,
     * 对于该字段的填写， 请参考 <a href="https://semver.org/lang/zh-CN/">语义化版本控制规范</a> 来进行.
     * @return 可空, 或任意字符串.
     */
    String version() default "1.0.0";


    /**
     * 声明该插件的依赖
     * <p>
     * 依赖指的是该插件对其他插件的依赖关系.
     * 该字段的每个项目代表一个依赖项, 请使用 "ID:版本" 的格式来声明依赖项, 以表示依赖关系.
     * 对需求版本的截取, 一般以最后一个冒号作为分割, 取右边部分的字符串需求版本项目.
     * 该条目支持绝大多数的语义版本规范标准, 因此可以随意选择习惯的规范进行填写.
     * <p>
     * 标准的依赖声明的示例如下:
     * <ul>
     *     <li>
     *         "example-plugin"             #不验证版本
     *     </li>
     *     <li>
     *         "example-plugin:>=1.2"       #需求版本大于等于1.2
     *     </li>
     * </ul>
     * <p>
     * 在运行时读取完所有插件的信息的情况下, 将会开始检查依赖情况.
     * 若依赖不存在, 则会抛出 {@link PluginException} 异常.
     * @return 可空, 若为空, 表示该插件没有依赖.
     */
    String[] dependencies() default { } ;

    /**
     * 声明该插件所使用的库
     * @return
     */
    String[] libraries() default  { } ;

    /**
     * 声明该插件的作者.
     * @return 可空, 或任意字符串.
     */
    String author() default "Anonymous";

}
