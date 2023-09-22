package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.config.bot.BotConfig;
import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;
import cafe.lunarconcerto.matrixcafe.api.common.BotManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MatrixCafeBotManager implements BotManager {

    private final List<Bot> botList = new ArrayList<>();

    public MatrixCafeBotManager() {
    }

    @Override
    public Bot createBot(BotConfig botConfig) {
        return null;
    }

    @Override
    public List<Bot> findBotById(String id) {
        List<Bot> resultList = new LinkedList<>();
//        botList.forEach(bots -> bots.stream().filter(bot -> bot.info().id().equals(id)).forEach(resultList::add));
        return resultList ;
    }

    @Override
    public int botCount() {
        return botList.size();
    }

}
