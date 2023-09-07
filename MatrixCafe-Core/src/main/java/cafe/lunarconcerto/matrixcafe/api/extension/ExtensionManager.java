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

    Optional<T> find(String id);

    List<T> all();

    boolean isEmpty();

    boolean isPresent();

}
