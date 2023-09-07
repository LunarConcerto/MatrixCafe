package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;
import cafe.lunarconcerto.matrixcafe.api.common.BotManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MatrixCafeBotManager implements BotManager {

    private final HashMap<String, List<Bot>> botMap ;

    public MatrixCafeBotManager() {
        botMap = new HashMap<>();
    }

    @Override
    public List<Bot> findByAdapterId(String adapter) {
        List<Bot> botList = botMap.get(adapter);
        return botList != null ? botList : Collections.emptyList() ;
    }

    @Override
    public List<Bot> findByBotId(String id) {
        List<Bot> resultList = new LinkedList<>();
        botMap.values().forEach(bots -> bots.stream().filter(bot -> bot.info().id().equals(id)).forEach(resultList::add));
        return resultList ;
    }

    @Override
    public void addBot(@NotNull Bot bot) {
        botMap.computeIfAbsent(bot.adapter().identifier(), k -> new LinkedList<>());
    }

    @Override
    public void addAllBot(@NotNull Collection<Bot> bots) {
        bots.stream()
                .findAny()
                .ifPresent(
                bot -> {
                    botMap.computeIfAbsent(bot.adapter().identifier(), k -> new LinkedList<>(bots));
                }
        );
    }

}
