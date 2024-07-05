package cafe.lunarconcerto.matrixcafe.api.plugin;

import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginClassContainer;

public class PluginException extends RuntimeException {

    /**
     * 出错的插件
     */
    protected PluginClassContainer container;

    public PluginException(PluginClassContainer container) {
        this.container = container;
    }

    public PluginException(String message, PluginClassContainer container) {
        super(message);
        this.container = container;
    }

    public PluginException(String message, Throwable cause, PluginClassContainer container) {
        super(message, cause);
        this.container = container;

    }

    public PluginException(Throwable cause, PluginClassContainer container) {
        super(cause);
        this.container = container;

    }

}
