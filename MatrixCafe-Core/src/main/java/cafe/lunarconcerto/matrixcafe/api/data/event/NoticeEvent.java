package cafe.lunarconcerto.matrixcafe.api.data.event;


import cafe.lunarconcerto.matrixcafe.api.application.Bus;
import cafe.lunarconcerto.matrixcafe.api.data.notice.Notice;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class NoticeEvent implements Event {

    protected Notice<?> notice;

    public NoticeEvent(Notice<?> notice) {
        this.notice = notice;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull NoticeEvent of(Notice<?> notice) {
        return new NoticeEvent(notice);
    }

    @Override
    public void consume() {
        Bus.cancelNoticeDelivery(this);
    }

    public Notice<?> getNotice() {
        return notice;
    }
}
