package cafe.lunarconcerto.matrixcafe.api.common;

import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;

import java.util.Collection;
import java.util.List;

public interface BotManager {

    List<Bot> findByAdapterId(String adapter);

    List<Bot> findByBotId(String id);

    void addBot(Bot bot);

    void addAllBot(Collection<Bot> bots);

}
