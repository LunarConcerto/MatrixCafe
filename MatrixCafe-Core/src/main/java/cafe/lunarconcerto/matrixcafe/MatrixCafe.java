package cafe.lunarconcerto.matrixcafe;

import cafe.lunarconcerto.matrixcafe.api.application.*;
import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationManager;
import cafe.lunarconcerto.matrixcafe.api.config.MatrixCafeConfiguration;
import cafe.lunarconcerto.matrixcafe.api.data.info.SystemInfo;
import cafe.lunarconcerto.matrixcafe.api.db.Database;
import cafe.lunarconcerto.matrixcafe.api.extension.ExtensionManager;
import cafe.lunarconcerto.matrixcafe.api.extension.ExtensionRegistry;
import cafe.lunarconcerto.matrixcafe.api.plugin.PluginManager;
import cafe.lunarconcerto.matrixcafe.api.extension.adapter.Adapter;
import cafe.lunarconcerto.matrixcafe.api.bot.BotManager;
import cafe.lunarconcerto.matrixcafe.api.schedule.*;
import cafe.lunarconcerto.matrixcafe.api.web.ws.WebSocketServer;
import cafe.lunarconcerto.matrixcafe.impl.MatrixCafeAdapterManager;
import cafe.lunarconcerto.matrixcafe.impl.MatrixCafeModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.quartz.SchedulerException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author LunarConcerto
 * @time 2023/8/6
 */
@Data
@Slf4j
@Singleton
@NoArgsConstructor
public class MatrixCafe {

    public static final String NAME = "MatrixCafe";

    public static final String VERSION = "0.1a" ;

    private static MatrixCafe matrixCafe;

    /**
     * 程序状态机
     */
    @Inject
    private ApplicationStateMachine stateMachine ;

    /**
     * 注入器
     */
    @Inject
    private Injector injector ;

    /**
     * 配置管理器
     */
    @Inject
    private ConfigurationManager configurationManager ;

    /**
     * 程序主配置类对象
     */
    @Inject
    private MatrixCafeConfiguration configuration;

    /**
     * 程序上下文
     */
    @Inject
    private ApplicationContext applicationContext ;

    /**
     * 扩展注册表
     */
    @Inject
    private ExtensionRegistry extensionRegistry ;

    /**
     * 时钟注册表
     */
    @Inject
    private ScheduleRegistry scheduleRegistry ;

    /**
     * 插件管理器
     */
    @Inject
    private PluginManager pluginManager ;

    /**
     * 数据库封装
     */
    @Inject
    private Database database ;

    /**
     * 机器人管理器
     */
    @Inject
    private BotManager botManager;

    /**
     * WS服务器
     */
    @Inject
    private WebSocketServer webSocketServer ;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * <h1>Matrix Cafe 应用程序框架的登录总入口</h1>
     * 调用该方法将开始启动整个Matrix Cafe的框架程序.
     * @param args 命令行参数
     */
    public static void launch(String[] args){
        Thread.currentThread().setName("MatrixCafe");
        log.info("## MatrixCafe application starting...");
        SystemInfo.create();
        try {
            Injector injector = Guice.createInjector(new MatrixCafeModule());
            log.info("\n"+ Banner.MATRIX_CAFE_BANNER);
            matrixCafe = injector.getInstance(MatrixCafe.class);
            matrixCafe.initialize(args);
        } catch (Exception e) {
            log.error("## MatrixCafe application catch a exception :", e);
        }
    }

    private void initialize(String[] args) throws Exception {
        // *0. 解析命令行参数
        parseCommandLine(args);
        // 1. 加载主配置文件
        log.info("-- 开始加载配置文件...");
        initializeConfiguration();
        setupExtensionManagers();
        // 2. 载入并设置插件
        log.info("-- 开始加载插件...");
        setupPlugins();
        // 3. 设置适配器
        log.info("-- 开始加载适配器...");
        setupAdapters();

        // 4. 初始化调度程序
        log.info("-- 初始化调度程序...");
        initializeSchedules();
        // 5. 初始化接口服务器
        log.info("-- 创建接口服务器...");
        initializeApiServer();
        // 6. 初始化程序状态机
        log.info("-- 启用程序状态机...");
        initializeStateMachine();

        // 7. 创建机器人
        log.info("-- 创建 Bot 中...");
        setupBots();

        log.info("-- Matrix Cafe 程序初始化已完成.");
    }

    private void setupExtensionManagers() {
        extensionRegistry.register(Adapter.class, new MatrixCafeAdapterManager());
    }

    private void parseCommandLine(String[] args) {
        // TODO
    }

    private void initializeConfiguration() throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(this::exceptionHandler);
    }

    private void setupPlugins() throws Exception {
        log.info("插件加载 1) 插件初始化...");
        pluginManager.initialize();
        log.info("插件加载 2) 插件实例化...");
        pluginManager.instantiate();
        log.info("插件加载 3) 插件启动中...");
        pluginManager.start();
        log.info("-- 插件加载完成! 总计 [{}] 个插件被加载.", pluginManager.loadedPluginCount());
    }

    private void setupAdapters(){
        Optional<ExtensionManager<Adapter>> managerOptional =
                extensionRegistry.findManager(Adapter.class);
        if (managerOptional.isEmpty()){
            log.error("### 适配器管理器未加载, 这应该是不可能出现的!!");
            return;
        }

        ExtensionManager<Adapter> manager = managerOptional.get();
        if (manager.isPresent()){
            return;
        }

        List<Adapter> extensions = extensionRegistry.find(Adapter.class);
        if (extensions.isEmpty()){
            if (configuration.isNoAdapterExit()){
                log.error("没有可用的协议适配器, 程序将退出.");
                Application.exit(0);
            }else{
                log.warn("警告:\t没有可用的协议适配器!");
            }
        }
    }

    private void initializeSchedules() throws SchedulerException {
        int heartbeatInterval = configuration.getHeartbeatInterval();
        if (heartbeatInterval > 0){
            scheduleRegistry.register(
                    ScheduleHelper.simpleJobDetail(HeartBeatJob.class, "Heartbeat"),
                    ScheduleHelper.simpleTrigger(CronHelper.everySeconds(heartbeatInterval), "Heartbeat")
            );
        }else {
            log.warn("心跳间隔为非正数, 系统心跳事件将关闭.");
        }

        scheduleRegistry.register(
                ScheduleHelper.simpleJobDetail(ChimeJob.class, "Chime"),
                ScheduleHelper.simpleTrigger(CronHelper.everyMinute(30), "Chime")
        );
        scheduleRegistry.start();
    }

    private void initializeApiServer(){
        webSocketServer.start();
    }

    private void initializeStateMachine() {
        stateMachine.enterState(ApplicationState.STANDBY);
    }

    private void setupBots(){
        stateMachine.enterState(ApplicationState.WORKING);
    }

    private void exceptionHandler(@NotNull Thread thread, Throwable e) {
        log.error("## MatrixCafe application catch a exception in thread {} :", thread.getName(), e);
        enterState(ApplicationState.ERROR);
    }

    public void enterState(ApplicationState state){
        if (stateMachine != null) {
            stateMachine.enterState(state);
        }
    }

    public static MatrixCafe getMatrixCafe() {
        return matrixCafe;
    }
}
