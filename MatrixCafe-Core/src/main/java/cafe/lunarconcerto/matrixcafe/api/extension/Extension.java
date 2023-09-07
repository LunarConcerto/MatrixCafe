package cafe.lunarconcerto.matrixcafe.api.extension;

/**
 * 标记一个类为扩展
 * <p>
 * 扩展可由 {@link ExtensionRegistry} 接收它一个实例,
 * 由 MatrixCafe 控制其生命周期以及启动程序.
 * <p>
 * 扩展在被注册时, 将可以获取程序资源的字段注入.
 * 使用 {@link javax.inject.Inject} 或 {@link jakarta.inject.Inject} 来标注要注入的字段.
 */
public interface Extension {

    /**
     * 该扩展的标识符id
     */
    String identifier();

}
