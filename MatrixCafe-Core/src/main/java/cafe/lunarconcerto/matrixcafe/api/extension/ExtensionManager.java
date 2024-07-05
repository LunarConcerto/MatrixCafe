package cafe.lunarconcerto.matrixcafe.api.extension;

import java.util.List;
import java.util.Optional;

/**
 * 扩展类子类管理器
 * <p>
 * 该类定义某一类扩展的管理类, 使得可以进行对某一特定类的统一管理操作.
 * @author LunarConcerto
 * @time 2023/8/2
 */
public interface ExtensionManager<T extends Extension> {

    void register(Extension extension);

    void unregister(Extension extension);

    /**
     * 按ID搜索已存在的扩展
     * @param id 目标id
     * @return 若没有找到，返回 Optional.empty()
     */
    Optional<T> find(String id);

    /**
     * 获取当前所有扩展
     * @return 扩展列表
     */
    List<T> all();

    /**
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * @return 是否存在可用扩展
     */
    boolean isPresent();

}
