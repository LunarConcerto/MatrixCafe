package cafe.lunarconcerto.matrixcafe.api.data.event.time;

import cafe.lunarconcerto.matrixcafe.api.config.MatrixCafeConfiguration;

/**
 * 心跳事件
 * <p>
 * 该事件以程序运行时间为基准, 每过去 {@link MatrixCafeConfiguration#getHeartbeatInterval()} 秒寄送一次该事件.
 */
public class HeartBeatEvent extends TimeEvent {

    public HeartBeatEvent() {

    }

}
