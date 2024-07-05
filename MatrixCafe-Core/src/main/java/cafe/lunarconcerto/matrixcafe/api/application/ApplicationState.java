package cafe.lunarconcerto.matrixcafe.api.application;

public enum ApplicationState {

    /**
     * 初始化状态,
     * <p>
     * 该状态为起始状态, 启动应用程序时自动进入该状态,
     * 初始化完成后程序进入 {@link ApplicationState#STANDBY} 状态.
     * 在该状态下, 系统运行各类初始化工作.
     */
    INITIALIZING,


    /**
     * 待命状态,
     * <p>
     * 在该状态下, 系统维持正常运行,
     * 但不进行消息处理, 此时可以进行设置等.
     */
    STANDBY,

    /**
     * 工作状态,
     * <p>
     * 在该状态下, 系统正常的接收以及处理消息
     */
    WORKING,

    /**
     * 错误状态,
     * <p>
     * 该状态下, 系统处于运行状态, 并尽可能输出错误日志
     */
    ERROR,

    /**
     * 调试状态
     * <p>
     * TODO
     */
    DEBUG,

    /**
     * 关闭状态,
     * <p>
     * 该状态为终结状态.
     * 该状态下,
     * 系统开始进行关闭逻辑, 最后执行程序退出.
     */
    CLOSING

}
