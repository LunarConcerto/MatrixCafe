package cafe.lunarconcerto.matrixcafe.api.protocol;

import cafe.lunarconcerto.matrixcafe.api.common.MessageMode;
import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationManager;
import cafe.lunarconcerto.matrixcafe.api.common.MessageResolver;
import cafe.lunarconcerto.matrixcafe.api.data.info.AdapterInfo;
import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import cafe.lunarconcerto.matrixcafe.api.extension.Extension;
import cafe.lunarconcerto.matrixcafe.api.web.ws.WebSocketClientInformation;
import io.javalin.websocket.WsMessageContext;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public interface Adapter extends Extension {

    void setup(MessageResolver resolver);

    AdapterInfo info();

    /**
     * 返回该类需求的配置类集合,
     * <p>
     * 对应类被读取后, 该类可由注入或重写 {@link Adapter#onLoadConfiguration(Object...)} 方法来获取对应配置类的实例.
     * @return 默认返回空, 或由子类重写得到返回值.
     */
    default Set<Class<?>> getRequireConfigurationType(){
        return Collections.emptySet();
    }

    /**
     * 当 {@link Adapter#getRequireConfigurationType()} 返回非空集时该方法得到调用
     * @param configs 由 {@link Adapter#getRequireConfigurationType()} 返回的类集合对应读取到的配置实例集合
     */
    default void onLoadConfiguration(Object... configs){

    }

    /**
     * 由该类自主控制的配置读取, 在程序生命周期中必然得到调用.
     * @param configurationManager 当前程序上下文中的配置管理器
     */
    default void onLoadConfiguration(ConfigurationManager configurationManager){

    }

    /**
     * 产生一个或多个对应该适配器协议的 Bot 对象
     */
    Collection<Bot> createBot();

    /**
     * 获取该适配器的标识符.
     * @return 非空字符串, 如果为空, 将引起异常.
     */
    @Override
    default String identifier(){
        return info().identifier();
    }

    /**
     * 重写该方法来注册一个对某 Socket 客户端的绑定.
     * 当程序检测到绑定的 Socket 客户端上线时,
     * 往后将使对应 Socket 客户端的消息重定向到该适配器的 {@link Adapter#onSocketMessage(WsMessageContext)} 方法.
     */
    default WebSocketClientInformation getSocketBindingInformation(){
        return null;
    }

    /**
     * 当该适配器重写 {@link Adapter#getSocketBindingInformation()} 方法后,
     * 当程序检测到绑定的 Socket 客户端上线时,
     * 往后将使对应 Socket 客户端的消息重定向到该适配器的该方法.
     * @param wsMessageContext 对应 Socket 客户端的消息
     */
    default void onSocketMessage(WsMessageContext wsMessageContext){

    }

    /**
     * 拉取消息
     * <p>
     * 调用该方法时
     * @return
     */
    default Message pullMessage(){
        return Message.NULL ;
    }

}
