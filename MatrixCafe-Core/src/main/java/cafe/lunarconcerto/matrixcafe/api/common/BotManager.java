package cafe.lunarconcerto.matrixcafe.api.common;

import cafe.lunarconcerto.matrixcafe.api.config.bot.BotConfig;
import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;

import java.util.Collection;
import java.util.List;

public interface BotManager {

    Bot createBot(BotConfig botConfig);

    List<Bot> findBotById(String id);

    int botCount();

}
