package cafe.lunarconcerto.matrixcafe.api.data.notice;

import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;

/**
 * 对于目标平台的通知的抽象,
 * 代表绝大部分除用户发出的文本信息外的信息来源.
 * 即是说, 这种通知通常不是由用户发出来的,
 * 而多半是由对应的协议端发送的信息.
 *
 * @param <T>
 */
public abstract class Notice<T> {

    /**
     * 通知的具体内容
     */
    protected T source;

    /**
     * 收到通知的 bot
     */
    protected Bot bot;

    public Notice(T source, Bot bot) {
        this.source = source;
        this.bot = bot;
    }

    public T getSource() {
        return source;
    }

    public Bot getBot() {
        return bot;
    }

}
