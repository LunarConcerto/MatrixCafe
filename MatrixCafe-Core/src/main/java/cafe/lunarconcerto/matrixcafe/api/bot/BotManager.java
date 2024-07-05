package cafe.lunarconcerto.matrixcafe.api.bot;

import java.util.List;
import java.util.Optional;

public interface BotManager {

    Bot createBot(BotConfig botConfig);

    Optional<Bot> findBotById(String id);

    int botCount();

}
