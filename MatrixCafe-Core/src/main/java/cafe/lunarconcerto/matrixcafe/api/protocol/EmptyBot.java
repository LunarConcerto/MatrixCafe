package cafe.lunarconcerto.matrixcafe.api.protocol;

import cafe.lunarconcerto.matrixcafe.api.data.message.BotMessage;
import cafe.lunarconcerto.matrixcafe.api.data.response.ProtocolResponse;

/**
 * @author LunarConcerto
 * @time 2023/8/17
 */
public class EmptyBot extends AbstractBot{

    @Override
    public ProtocolResponse<?> send(BotMessage message) {
        return null ;
    }

    @Override
    public void sendAsync(BotMessage message) {

    }

    @Override
    public void disable() {

    }

    @Override
    public void enable() {

    }

    @Override
    public boolean isAsyncSender() {
        return false;
    }

}
