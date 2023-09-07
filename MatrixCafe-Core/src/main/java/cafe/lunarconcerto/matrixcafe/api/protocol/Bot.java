package cafe.lunarconcerto.matrixcafe.api.protocol;


import cafe.lunarconcerto.matrixcafe.api.data.info.BotInfo;
import cafe.lunarconcerto.matrixcafe.api.data.message.BotMessage;
import cafe.lunarconcerto.matrixcafe.api.data.response.ProtocolResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Bot {

    /**
     * 获取该 Bot 的元信息
     * @return 非空的元信息对象
     */
    @NotNull BotInfo info();

    /**
     * 获取产生该 Bot 的源适配器
     * @return 非空适配器对象, 如果为空, 将引起异常.
     */
    @NotNull Adapter adapter();

    /**
     * 向该 Bot 发送一条消息.
     * <p>
     * 该消息应为由 MatrixCafe 抽象的消息类对象.
     * 由 Bot 实现转化为对应平台的消息并发出.
     * @param message 消息对象
     * @return 可能存在的响应
     */
    ProtocolResponse<?> send(BotMessage message);

    /**
     * 向该 Bot 发送一条异步消息.
     * <p>
     * 对于异步消息, 发出后往往无法立即收到回复.
     * <p>
     * 该消息应为由 MatrixCafe 抽象的消息类对象.
     * 由 Bot 实现转化为对应平台的消息并发出.
     * @param message 消息对象
     */
    void sendAsync(BotMessage message);

    /**
     * 禁用 Bot
     * <p>
     * 调用该方法则使得该 Bot 停止处理消息收发.
     */
    void disable();

    /**
     * 启用 Bot
     * <p>
     * 调用该方法则使得该 Bot 重新开始处理消息手法.
     */
    void enable();

    /**
     * 指示该 Bot 是否是异步的消息发送者.
     * <p>
     * 如果是异步的消息发送者, 则启用无返回值类型的send方法.
     */
    boolean isAsyncSender();

    default boolean isSyncSender(){
        return !isAsyncSender();
    }

}
