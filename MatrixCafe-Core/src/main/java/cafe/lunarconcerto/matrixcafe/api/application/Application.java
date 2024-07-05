package cafe.lunarconcerto.matrixcafe.api.application;

import cafe.lunarconcerto.matrixcafe.MatrixCafe;
import cafe.lunarconcerto.matrixcafe.api.data.event.lifecycle.ExitEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LunarConcerto
 * @time 2023/7/7
 */
@Slf4j
public class Application {

    public MatrixCafe getMatrixCafe(){
        return MatrixCafe.getMatrixCafe();
    }

    /**
     * 退出程序
     * @param exitStatus 退出代码
     */
    public static void exit(int exitStatus){
        log.info("## MatrixCafe application closing...");

        ExitEvent exitEvent = new ExitEvent();

        MatrixCafe.getMatrixCafe().getPluginManager().disableAll();

        Bus.postSystemEvent(exitEvent);

        System.exit(exitStatus);
    }

    public static String pluginDirectoryPath(){
        return DirectoryConstants.PLUGIN_DIR.path();
    }

    public static String cacheDirectoryPath(){
        return DirectoryConstants.CACHE_DIR.path();
    }

    public static String configurationDirectoryPath(){
        return DirectoryConstants.CONFIG_DIR.path();
    }

    public static String workplaceDirectoryPath(){
        return DirectoryConstants.MAIN_DIR.path();
    }

}
