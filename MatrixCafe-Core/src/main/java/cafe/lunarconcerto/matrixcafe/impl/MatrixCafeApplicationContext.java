package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.common.ApplicationContext;
import cafe.lunarconcerto.matrixcafe.api.db.Database;
import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationManager;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Singleton
@SuppressWarnings("unchecked")
public class MatrixCafeApplicationContext implements ApplicationContext {

    @Inject
    private Injector injector;

    @Inject
    private ConfigurationManager manager ;

    @Inject
    private Database database ;

    private final Map<String, Object> objects ;

    public MatrixCafeApplicationContext() {
        objects = new HashMap<>();
    }

    @Override
    public <T> Optional<T> get(String name) {
        return Optional.of((T) objects.get(name));
    }

    @Override
    public <T> Optional<T> get(Class<T> type) {
        Optional<?> optional = objects.values().stream().filter(o -> o.getClass().equals(type)).findFirst();
        if (optional.isEmpty()){
            return Optional.ofNullable(injector.getInstance(type));
        }else {
            return (Optional<T>) optional;
        }
    }

    @Override
    public boolean has(String name) {
        return objects.containsKey(name) ;
    }

    @Override
    public boolean has(Class<?> type) {
        return objects.values().stream().anyMatch(o -> o.getClass().equals(type));
    }

    @Override
    public ApplicationContext add(@NotNull Object object) {
        InApplicationContextName name = object.getClass().getAnnotation(InApplicationContextName.class);
        if (name != null){
            objects.put(name.value(), object);
        }else {
            objects.put(object.getClass().getName(), object);
        }
        return this;
    }

    @Override
    public ApplicationContext add(String name, Object object) {
        objects.put(name, object);
        return this;
    }

    @Override
    public ConfigurationManager configurationManager() {
        return manager;
    }

    @Override
    public Database database() {
        return database;
    }

}
