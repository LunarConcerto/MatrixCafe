package cafe.lunarconcerto.matrixcafe.api.data.event;

import cafe.lunarconcerto.matrixcafe.api.application.Bus;

public class SystemEvent implements Event {

    @Override
    public void consume() {
        Bus.cancelSystemEventDelivery(this);
    }

}
