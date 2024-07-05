package cafe.lunarconcerto.matrixcafe.api.data.message;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.TextContent;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionMetaInfo;
import cafe.lunarconcerto.matrixcafe.api.data.user.Sender;
import cafe.lunarconcerto.matrixcafe.api.bot.Bot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

/**
 * @author LunarConcerto
 * @time 2023/9/24
 */
@Getter
@NoArgsConstructor
public final class MessageBuilder {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Bot bot;

    private MessageContent contents;

    private SessionMetaInfo sessionMetaInfo;

    private Sender<?> sender;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Nonnull
    public MessageBuilder withBot(@Nonnull Bot bot) {
        this.bot = bot;
        return this;
    }
    @Nonnull
    public MessageBuilder withContents(@Nonnull MessageContent contents) {
        this.contents = contents;
        return this;
    }

    @Nonnull
    public MessageBuilder withStringContents(@Nonnull String contents) {
        this.contents = TextContent.of(contents);
        return this;
    }
    @Nonnull
    public MessageBuilder withSessionMetaInfo(@Nonnull SessionMetaInfo sessionMetaInfo) {
        this.sessionMetaInfo = sessionMetaInfo;
        return this;
    }
    @Nonnull
    public MessageBuilder withSender(@Nonnull Sender<?> sender) {
        this.sender = sender;
        return this;
    }

    @Nonnull
    public Message build() {
        return new Message(this);
    }
}
