package cafe.lunarconcerto.matrixcafe.api.data.event;

import cafe.lunarconcerto.matrixcafe.api.application.Bus;
import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MessageEvent implements Event {

    protected Message message;

    public MessageEvent(Message message) {
        this.message = message;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MessageEvent of(Message message) {
        return new MessageEvent(message);
    }

    @Override
    public void consume() {
        Bus.cancelMessageDelivery(this);
    }

    public Message getMessage() {
        return message;
    }
}
