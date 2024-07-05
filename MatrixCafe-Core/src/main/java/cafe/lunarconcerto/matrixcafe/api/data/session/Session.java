package cafe.lunarconcerto.matrixcafe.api.data.session;

import cafe.lunarconcerto.matrixcafe.api.bot.Bot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <h1>会话</h1>
 * <p/>
 * 会话用来代表一个"聊天窗口"，
 * 例如QQ群，或是与某人的一个私聊。
 * <p/>
 * 一般情况下一个会话中除了{@link Session#info} 必须由适配器提供以外,
 * 其余成员将由 {@link SessionManager} 接管提供.
 * <p/>
 * 会话是一个需要被缓存的对象, 由于同一个会话不存在一直进行的概率,
 * 故当此对象保持一段时间无活跃使用时, 该对象应该被回收.
 */
@Getter
@RequiredArgsConstructor
public abstract class Session {

    /**
     * 会话的元信息, 由协议适配器提供.
     */
    protected final SessionMetaInfo info ;

    /**
     * 该会话由哪个 Bot 产生.
     */
    protected final Bot bot;

    /**
     * 该会话的消息历史记录
     */
    protected final SessionMessageHistory history = new SessionMessageHistory();

    /**
     * 该会话的独立配置
     */
    protected SessionConfig config;

}
