package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.extension.Extension;
import cafe.lunarconcerto.matrixcafe.api.extension.ExtensionManager;
import cafe.lunarconcerto.matrixcafe.api.extension.ExtensionRegistry;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class MatrixCafeExtensionRegistry implements ExtensionRegistry {

    /**
     * 将为所有扩展注入依赖
     */
    @Inject
    private Injector injector;

    private final Map<String, Extension> extensionMap ;

    private final Map<Class<? extends Extension>, ExtensionManager<? extends Extension>> extensionManagerMap;

    public MatrixCafeExtensionRegistry() {
        extensionMap = new ConcurrentHashMap<>();
        extensionManagerMap = new ConcurrentHashMap<>();
    }

    @Override
    public void register(@NotNull Extension extension) {
        ExtensionManager<? extends Extension> manager = extensionManagerMap.get(extension.getClass());
        if (manager != null){
            manager.register(extension);
        }

        // 安全检查
        if (extensionMap.containsKey(extension.identifier())){
            log.warn("注册的扩展ID {} 已存在, 类名为 {}. 同名扩展类名为 {}",
                    extension.identifier(),
                    extension.getClass().getName(),
                    extensionMap.get(extension.identifier()).getClass().getName());
            return;
        }

        log.info("注册扩展 => {}, 类型 => {}", extension.identifier(), extension.getClass().getSimpleName());
        extensionMap.put(extension.identifier(), extension);
        // 注入字段
        injector.injectMembers(extension);
    }

    public <T extends Extension> void register(Class<T> manageExtensionType, ExtensionManager<T> extensionManager) {
        extensionManagerMap.put(manageExtensionType, extensionManager);
    }

    @Override
    public void unregister(@NotNull Extension extension) {
        extensionMap.remove(extension.identifier());
    }

    @Override
    public <T extends Extension> void unregister(Class<T> manageExtensionType) {
        extensionManagerMap.remove(manageExtensionType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Extension> List<T> find(Class<T> type) {
        if (extensionManagerMap.containsKey(type)){
            ExtensionManager<T> manager = (ExtensionManager<T>) extensionManagerMap.get(type);
            return manager.all();
        }

        return extensionMap.values()
                .stream()
                .filter(extension -> type.isAssignableFrom(extension.getClass()))
                .map(item -> (T) item)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<Extension> find(String name) {
        return Optional.of(extensionMap.get(name));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Extension> Optional<ExtensionManager<T>> findManager(Class<T> type) {
        return Optional.of((ExtensionManager<T>) extensionManagerMap.get(type));
    }

}
