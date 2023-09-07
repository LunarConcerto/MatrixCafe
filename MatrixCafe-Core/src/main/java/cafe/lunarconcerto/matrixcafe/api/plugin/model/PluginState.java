package cafe.lunarconcerto.matrixcafe.api.plugin.model;

public enum PluginState {

    /**
     * 表示该插件已读取, 存在基本信息, 但尚未实例化.
     */
    LOADED("LOADED"),

    /**
     * 表示该插件的前期准备已就绪, 即依赖关系已确认, 主类成功地实例化, 准备被运行.
     */
    READY("READY"),

    /**
     * 表示该插件正在正常运行.
     */
    RUNNING("RUNNING"),

    /**
     * 表示该插件处于关闭状态.
     * 关闭状态一般由用户操作导致进入,
     * 该状态只是暂时性的, 服务关闭时不会记录该状态.
     */
    CLOSED("CLOSED"),

    /**
     * 表示该插件被用户主动禁用, 不能被运行.
     * 禁用状态是永久性的, 服务关闭时会记录该状态,
     * 重启服务后状态将持续, 直到被用户主动取消禁用.
     */
    DISABLE("DISABLE"),

    /**
     * 表示该插件在启动或运行时发生了无法维持的错误,
     * 导致该插件处于无法继续运行的状态.
     */
    ERROR("ERROR");

    private final String status ;


    PluginState(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
