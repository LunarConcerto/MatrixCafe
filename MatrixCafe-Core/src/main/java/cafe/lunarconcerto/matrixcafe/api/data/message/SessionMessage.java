package cafe.lunarconcerto.matrixcafe.api.data.message;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.TextContent;
import cafe.lunarconcerto.matrixcafe.api.data.response.ProtocolData;
import cafe.lunarconcerto.matrixcafe.api.data.session.Session;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionConfig;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionMessageHistory;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionMetaInfo;
import cafe.lunarconcerto.matrixcafe.api.bot.Bot;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.VisibleForTesting;

/**
 * 消息与会话的组合, 最终交由响应器响应的对象
 * 该资源可能被复用.
 */
@Getter
public class SessionMessage {

    protected Message message ;

    protected final Session session ;

    public SessionMessage(Session session) {
        this.session = session;
    }

    /**
     * 生成一条仅包含文本而不包含 Session 的消息
     * 仅推荐在测试环境下使用
     * @param text 目标文本
     * @return 仅包含文本内容的消息对象
     */
    @TestOnly
    @VisibleForTesting
    @Contract("_ -> new")
    public static @NotNull SessionMessage simpleText(String text){
        return new SessionMessage(Message.simpleText(text), null);
    }

    public SessionMessage(Message message, Session session) {
        this.message = message;
        this.session = session;
    }

    public BotMessage simplyReply(MessageContent content){
        return new BotMessage(this, content);
    }

    public ProtocolData<?> sendReply(MessageContent content){
        return session.getBot().send(simplyReply(content));
    }

    public ProtocolData<?> sendReply(String text){
        return session.getBot().send(simplyReply(new TextContent(text)));
    }

    public void sendReplyAsync(MessageContent content){
        session.getBot().sendAsync(simplyReply(content));
    }

    public void sendReplyAsync(String text){
        session.getBot().sendAsync(simplyReply(new TextContent(text)));
    }

    /**
     * 获取消息内容
     * @return message.getContents();
     */
    public MessageContent getContents() {
        return message.getContents();
    }

    /**
     * 获取文本化消息内容
     * @return message.getContents().text();
     */
    public String getTextContents(){
        return message.getContents().text();
    }

    public long getId() {
        return message.getId();
    }

    public Bot getBot() {
        return message.getBot();
    }

    public SessionMetaInfo getSessionMetaInfo() {
        return message.getSessionMetaInfo();
    }


    public SessionMetaInfo getInfo() {
        return session.getInfo();
    }

    public SessionMessageHistory getHistory() {
        return session.getHistory();
    }

    public SessionConfig getConfig() {
        return session.getConfig();
    }
}
