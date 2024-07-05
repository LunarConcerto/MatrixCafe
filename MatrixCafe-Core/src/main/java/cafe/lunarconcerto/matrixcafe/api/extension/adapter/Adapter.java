package cafe.lunarconcerto.matrixcafe.api.extension.adapter;

import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationManager;
import cafe.lunarconcerto.matrixcafe.api.data.info.AdapterInfo;
import cafe.lunarconcerto.matrixcafe.api.data.message.BotMessage;
import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import cafe.lunarconcerto.matrixcafe.api.data.message.MessageQueue;
import cafe.lunarconcerto.matrixcafe.api.data.response.ProtocolData;
import cafe.lunarconcerto.matrixcafe.api.bot.Bot;
import cafe.lunarconcerto.matrixcafe.api.bot.ProtocolAccount;
import cafe.lunarconcerto.matrixcafe.api.extension.Extension;
import cafe.lunarconcerto.matrixcafe.api.web.ws.WebSocketClientValidator;
import io.javalin.websocket.WsMessageContext;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Set;


public abstract class Adapter implements Extension {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private AdapterInfo adapterInfo;

    @Inject
    protected ConfigurationManager configurationManager ;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Public Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * @return 该适配器信息对象
     */
    public AdapterInfo getAdapterInfo(){
        return adapterInfo;
    }

    /**
     * 返回该类需求的配置类集合,
     * <p>
     * 对应类被读取后, 该类可由注入或重写 {@link Adapter#onLoadConfiguration(Object...)} 方法来获取对应配置类的实例.
     * @return 默认返回空, 或由子类重写得到返回值.
     */
    public Set<Class<?>> getRequireConfigurationType(){
        return Collections.emptySet();
    }

    /**
     * 当 {@link Adapter#getRequireConfigurationType()} 返回非空集时该方法得到调用
     * @param configs 由 {@link Adapter#getRequireConfigurationType()} 返回的类集合对应读取到的配置实例集合
     */
    public void onLoadConfiguration(Object... configs){

    }

    /**
     * 由该类自主控制的配置读取, 在程序生命周期中必然得到调用.
     * @param configurationManager 当前程序上下文中的配置管理器
     */
    public void onLoadConfiguration(ConfigurationManager configurationManager){

    }

    /**
     * 拉取消息
     * <p>
     * 调用该方法时, 适配器应返回一条待处理的消息.
     * @return 若不存在需要处理的消息, 返回 {@link Message#NULL}
     */
    public Message pullMessage(){
        return Message.NULL ;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Protected Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected <T extends Message> MessageQueue<T> createQueue(){
        return MessageQueue.create();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Abstract Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * 向该 Bot 发送一条消息.
     * <p>
     * 该消息应为由 MatrixCafe 抽象的消息类对象.
     * 由 适配器 实现转化为对应平台的消息并发出.
     * @param message 消息对象
     * @return 可能存在的响应
     */
    public abstract ProtocolData<?> send(BotMessage message);

    public abstract void sendAsync(BotMessage message);

    public abstract ProtocolAccount bind(Bot bot);

    public abstract void unbind(Bot bot);

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Override Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * 获取该适配器的标识符.
     * @return 非空字符串, 如果为空, 将引起异常.
     */
    @Override
    public String identifier(){
        return getAdapterInfo().identifier();
    }

}
