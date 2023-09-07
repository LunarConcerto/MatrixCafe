package cafe.lunarconcerto.matrixcafe.api.common;

import cafe.lunarconcerto.matrixcafe.api.protocol.Adapter;

/**
 * 对消息处理的模式.
 * <p>
 * 默认情况下程序采用被动运行,
 * 是否需要采用主动模式依据适配器的要求来确定.
 * @author LunarConcerto
 * @time 2023/8/17
 */
public enum MessageMode {

    /**
     * 主动模式
     * <p>
     * 主动模式下, 系统开启主循环进行主动更新,
     * 期间通过调用 {@link Adapter#pullMessage()} 来主动取得消息并进行处理.
     */
    ACTIVE,

    /**
     * 被动模式
     * <p>
     * 被动模式下, 系统不开启主循环, 只进行基本的按时调度,
     * 期间等待适配器等或插件的自主调用消息处理方法.
     */
    PASSIVE

}
