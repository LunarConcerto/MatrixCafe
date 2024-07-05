package cafe.lunarconcerto.matrixcafe.api.extension;

import java.util.List;
import java.util.Optional;

/**
 * <h1>扩展注册器</h1>
 */
public interface ExtensionRegistry {

    /**
     * 注册一个扩展
     * @param extension 目标扩展
     */
    void register(Extension extension);

    <T extends Extension> void register(Class<T> manageExtensionType, ExtensionManager<T> extensionManager);

    void unregister(Extension extension);

    <T extends Extension> void unregister(Class<T> manageExtensionType);

    <T extends Extension> List<T> find(Class<T> type);

    Optional<Extension> find(String name);

    <T extends Extension> Optional<ExtensionManager<T>> findManager(Class<T> type);
}
