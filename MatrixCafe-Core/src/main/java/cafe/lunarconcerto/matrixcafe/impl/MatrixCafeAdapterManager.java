package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.extension.Extension;
import cafe.lunarconcerto.matrixcafe.api.extension.ExtensionManager;
import cafe.lunarconcerto.matrixcafe.api.extension.adapter.Adapter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author LunarConcerto
 * @time 2023/8/6
 */
@Slf4j
public class MatrixCafeAdapterManager implements ExtensionManager<Adapter> {

    private final Set<Adapter> adapterSet;

    public MatrixCafeAdapterManager() {
        adapterSet = new HashSet<>();
    }

    @Override
    public void register(Extension extension) {
        if (notAdapter(extension)) return;
        adapterSet.add((Adapter) extension);
    }

    @Override
    public void unregister(Extension extension) {
        if (notAdapter(extension)) return;
        adapterSet.remove((Adapter) extension);
    }

    private boolean notAdapter(Extension extension){
        if (!(extension instanceof Adapter)){
            log.info("### 可疑的类型注册 - 接收到的实例 {}(类型:{}) 不属于 Adapter, 因此 AdapterManager 未进行任何操作.", extension.identifier(), extension.getClass());
            return true;
        }

        return false;
    }

    @Override
    public Optional<Adapter> find(String id) {
        return adapterSet.stream().filter(adapter -> adapter.identifier().equals(id)).findFirst();
    }

    @Override
    public List<Adapter> all() {
        return adapterSet.stream().toList();
    }

    @Override
    public boolean isEmpty() {
        return adapterSet.isEmpty();
    }

    @Override
    public boolean isPresent() {
        return !isEmpty();
    }

}
