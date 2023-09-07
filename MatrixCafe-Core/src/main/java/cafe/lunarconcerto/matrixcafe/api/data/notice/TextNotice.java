package cafe.lunarconcerto.matrixcafe.api.data.notice;

import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;

public class TextNotice extends Notice<String> {

    public TextNotice(String source, Bot bot) {
        super(source, bot);
    }

}
