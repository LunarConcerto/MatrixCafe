package cafe.lunarconcerto.matrixcafe.api.responder.command;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;

public interface CommandParser {

    CommandActionData parse(SessionMessage message);

}
