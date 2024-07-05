package cafe.lunarconcerto.matrixcafe.api.data.message;


import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.session.Session;
import org.jetbrains.annotations.NotNull;

public class BotMessage extends SessionMessage {

    public BotMessage(Session session) {
        super(session);
    }

    public BotMessage(@NotNull SessionMessage message, MessageContent content){
        super(message.getSession());
        this.message = Message.newBuilder()
                .withBot(message.getBot())
                .withSessionMetaInfo(message.getSessionMetaInfo())
                .withContents(content)
                .build();
    }

}
