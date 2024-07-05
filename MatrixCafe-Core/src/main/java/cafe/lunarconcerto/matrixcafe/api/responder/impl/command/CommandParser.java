package cafe.lunarconcerto.matrixcafe.api.responder.impl.command;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;

public interface CommandParser {

    CommandActionParam parse(SessionMessage message);

}
