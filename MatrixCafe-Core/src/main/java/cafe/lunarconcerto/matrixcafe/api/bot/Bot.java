package cafe.lunarconcerto.matrixcafe.api.bot;


import cafe.lunarconcerto.matrixcafe.api.data.message.BotMessage;
import cafe.lunarconcerto.matrixcafe.api.data.message.MessageBuilder;
import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.data.response.ProtocolData;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionManager;
import cafe.lunarconcerto.matrixcafe.api.extension.adapter.Adapter;
import cafe.lunarconcerto.matrixcafe.api.responder.ResponderRegistry;
import jakarta.inject.Inject;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Bot</h1>
 * Bot 是一个独立运行的线程
 */
@Data
public class Bot {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Constant
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static final Bot NULL = new Bot();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Inject
    protected ResponderRegistry registry;

    @Inject
    protected SessionManager sessionManager ;

    /**
     * Bot 唯一ID
     * <p>
     * 该ID一般由系统生成
     */
    private String instanceID;

    protected BotConfig config;

    protected Adapter adapter;

    protected ProtocolAccount protocolAccount;

    protected boolean isActive = false ;

    /**
     * 指示该 Bot 是否是异步的消息发送者.
     * <p>
     * 如果是异步的消息发送者, 则启用无返回值类型的send方法.
     */
    protected boolean asyncSender;

    protected BotWorker botWorker = null ;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public Bot(BotConfig config) {
        this.config = config;

        initialize(config);
    }

    private Bot() { config = null; }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Private Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected void initialize(@NotNull BotConfig config){
        if (config.isEnableBotAction()){
            botWorker = new BotWorker();
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Public Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * <h1>处理一条新信息</h1>
     * <p>
     * 该方法一般由适配器进行调用, 适配器对 Bot 进行绑定后, 将特定消息转发到对应的 Bot 的该方法进行处理.
     * @param messageBuilder 目标信息
     */
    public void resolveMessage(@NotNull MessageBuilder messageBuilder){
        messageBuilder.withBot(this);

        SessionMessage sessionMessage = sessionManager.warp(messageBuilder.build());
    }

    /**
     * <h1>向 Bot 发送一条消息.</h1>
     * <p>
     * 该消息应为由 MatrixCafe 抽象的消息类对象.
     * 由 适配器 实现转化为对应平台的消息并发出.
     * @param message 消息对象
     * @return 可能存在的响应
     */
    public ProtocolData<?> send(BotMessage message){
        return adapter.send(message);
    }

    /**
     * <h1>向 Bot 发送一条异步消息.</h1>
     * <p>
     * 对于异步消息, 发出后往往无法立即收到回复.
     * 因此该方法没有返回值.
     * 也可以将此方法当做一个忽略返回值的 {@link Bot#send(BotMessage)} 方法使用.
     * <p>
     * 该消息应为由 MatrixCafe 抽象的消息类对象.
     * 由 Bot 实现转化为对应平台的消息并发出.
     * @param message 消息对象
     */
    public void sendAsync(BotMessage message){
        adapter.sendAsync(message);
    }

    /**
     * <h1>禁用 Bot</h1>
     * <p>
     * 调用该方法则使得该 Bot 停止处理消息收发.
     */
    public void disable(){
        adapter.unbind(this);

        isActive = false;
    }

    /**
     * <h1>启用 Bot</h1>
     * <p>
     * 调用该方法则使得该 Bot 重新开始处理消息收发.
     */
    public void enable(){
        protocolAccount = adapter.bind(this);

        isActive = true;
    }

    public void bindAdapter(Adapter newAdapter){
        adapter = newAdapter;
        protocolAccount = adapter.bind(this);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Setter / Getter
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void setAdapter(Adapter adapter) {
        bindAdapter(adapter);
    }
}
