package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.bot.BotConfig;
import cafe.lunarconcerto.matrixcafe.api.bot.Bot;
import cafe.lunarconcerto.matrixcafe.api.bot.BotManager;
import cafe.lunarconcerto.matrixcafe.api.extension.adapter.Adapter;

import java.util.*;

public class MatrixCafeBotManager implements BotManager {

    private final List<Bot> botList = new ArrayList<>();

    public MatrixCafeBotManager() { }

    @Override
    public Bot createBot(BotConfig botConfig) {
        return createBot(botConfig, null);
    }

    public Bot createBot(BotConfig botConfig, Adapter adapter) {
        Bot bot = new Bot(botConfig);
        bot.bindAdapter(adapter);
        botList.add(bot);
        return bot;
    }

    @Override
    public Optional<Bot> findBotById(String id) {
        return botList.stream()
                .filter(bot -> bot.getConfig().getId().equals(id))
                .findFirst();
    }

    @Override
    public int botCount() {
        return botList.size();
    }

}
