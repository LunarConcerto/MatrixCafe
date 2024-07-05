package cafe.lunarconcerto.matrixcafe.api.application;

import cafe.lunarconcerto.matrixcafe.api.data.event.time.HeartBeatEvent;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Singleton
public final class ApplicationStateMachine {

    private ApplicationState state ;

    private final HashMap<ApplicationState, List<Runnable>> onApplicationStateUpdateActionMap;

    private final HashMap<ApplicationState, List<Runnable>> onApplicationStateEnterActionMap;

    public ApplicationStateMachine() {
        onApplicationStateUpdateActionMap = new HashMap<>();
        onApplicationStateEnterActionMap = new HashMap<>();
        Bus.registerSystemEventListener(this);
        enterState(ApplicationState.INITIALIZING);
    }

    public void registerOnStateUpdateListener(ApplicationState state, Runnable runnable){
        List<Runnable> list = onApplicationStateUpdateActionMap.computeIfAbsent(state, k -> new LinkedList<>());
        list.add(runnable);
    }

    public void registerOnStateEnterListener(ApplicationState state, Runnable runnable){
        List<Runnable> list = onApplicationStateEnterActionMap.computeIfAbsent(state, k -> new LinkedList<>());
        list.add(runnable);
    }

    @Subscribe
    public void stateMachine(HeartBeatEvent event){
        runIfPresent(onApplicationStateUpdateActionMap);
    }

    public void enterState(ApplicationState state) {
        this.state = state;
        runIfPresent(onApplicationStateEnterActionMap);
    }

    public void error(Throwable e){
        enterState(ApplicationState.ERROR);
    }

    private void runIfPresent(@NotNull HashMap<ApplicationState, List<Runnable>> map){
        if (map.containsKey(state)) map.get(state).forEach(Runnable::run);
    }
}
