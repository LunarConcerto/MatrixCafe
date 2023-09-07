package cafe.lunarconcerto.matrixcafe.api.data.message;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.TextContent;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionMetaInfo;
import cafe.lunarconcerto.matrixcafe.api.data.user.Sender;
import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;
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

    public static final Message NULL = new Message();

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
    protected SessionMetaInfo sessionMetaInfo;

    protected Sender<?> sender ;

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
        return Message.newBuilder().contents(TextContent.of(text)).build();
    }

    @Contract(pure = true)
    private Message(@NotNull Builder builder) {
        bot = builder.bot;
        contents = builder.contents;
        sessionMetaInfo = builder.sessionMetaInfo;
        sender = builder.sender;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Message of(Bot bot, MessageContent contents, SessionMetaInfo sessionMetaInfo){
        return new Message(bot, contents, sessionMetaInfo);
    }

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

    public static @NotNull Builder newBuilder(@Nonnull Message copy) {
        Builder builder = new Builder();
        builder.bot = copy.getBot();
        builder.contents = copy.getContents();
        builder.sessionMetaInfo = copy.getSessionMetaInfo();
        builder.sender = copy.getSender();
        return builder;
    }

    protected long generateId() {
        return sessionMetaInfo.hashCode() + System.currentTimeMillis();
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Bot bot;
        private MessageContent contents;
        private SessionMetaInfo sessionMetaInfo;
        private Sender<?> sender;

        private Builder() {
        }

        @Nonnull
        public Builder bot(@Nonnull Bot bot) {
            this.bot = bot;
            return this;
        }

        @Nonnull
        public Builder contents(@Nonnull MessageContent contents) {
            this.contents = contents;
            return this;
        }

        @Nonnull
        public Builder sessionMetaInfo(@Nonnull SessionMetaInfo sessionMetaInfo) {
            this.sessionMetaInfo = sessionMetaInfo;
            return this;
        }

        @Nonnull
        public Builder sender(@Nonnull Sender<?> sender) {
            this.sender = sender;
            return this;
        }

        @Nonnull
        public Message build() {
            return new Message(this);
        }
    }
}
