package cafe.lunarconcerto.matrixcafe.api.data.message;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.TextContent;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionMetaInfo;
import cafe.lunarconcerto.matrixcafe.api.data.user.Sender;
import cafe.lunarconcerto.matrixcafe.api.bot.Bot;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.VisibleForTesting;

import javax.annotation.Nonnull;

/**
 * 消息类是对所有的有关用户在某个上下文中发送的一条消息的抽象.
 */
@Getter
@ToString
public class Message {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Constants
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static final Message NULL = new Message();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * 这条消息的标识符ID
     */
    protected long id ;

    /**
     * 处理这条消息的 bot
     */
    protected Bot bot ;

    /**
     * 消息的具体内容
     */
    protected MessageContent contents;

    /**
     * 会话元信息
     */
    protected SessionMetaInfo sessionMetaInfo = SessionMetaInfo.NULL;

    protected Sender<?> sender ;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Static Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * 生成一个只包含文本, 而不包含其他信息的消息对象
     * 仅推荐在测试环境下使用
     * @param text 目标文本
     * @return 仅包含文本内容的消息对象
     */
    @TestOnly
    @VisibleForTesting
    @Contract("_ -> new")
    public static @NotNull Message simpleText(String text){
        return Message.newBuilder().withContents(TextContent.of(text)).build();
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Message of(Bot bot, MessageContent contents, SessionMetaInfo sessionMetaInfo){
        return new Message(bot, contents, sessionMetaInfo);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected Message() {
        id = generateId();
    }

    protected Message(Bot bot, MessageContent contents, SessionMetaInfo sessionMetaInfo) {
        this.bot = bot;
        this.contents = contents;
        this.sessionMetaInfo = sessionMetaInfo;
        id = generateId();
    }

    protected Message(Bot bot, MessageContent contents, SessionMetaInfo sessionMetaInfo, Sender<?> sender) {
        this.bot = bot;
        this.contents = contents;
        this.sessionMetaInfo = sessionMetaInfo;
        this.sender = sender;
        id = generateId();
    }

    @Contract(pure = true)
    Message(@NotNull MessageBuilder builder) {
        bot = builder.getBot();
        contents = builder.getContents();
        sessionMetaInfo = builder.getSessionMetaInfo();
        sender = builder.getSender();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Protected Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected long generateId() {
        return sessionMetaInfo.hashCode() + System.currentTimeMillis();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Inner Builder
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static @NotNull MessageBuilder newBuilder(@Nonnull Message copy) {
        MessageBuilder builder = new MessageBuilder();
        builder.withBot(copy.getBot());
        builder.withContents(copy.getContents());
        builder.withSessionMetaInfo(copy.getSessionMetaInfo());
        builder.withSender(copy.getSender());
        return builder;
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull MessageBuilder newBuilder() {
        return new MessageBuilder();
    }

}
