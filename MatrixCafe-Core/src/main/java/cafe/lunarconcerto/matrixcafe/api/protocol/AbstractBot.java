package cafe.lunarconcerto.matrixcafe.api.protocol;


import cafe.lunarconcerto.matrixcafe.api.data.info.BotInfo;
import cafe.lunarconcerto.matrixcafe.api.data.message.BotMessage;
import cafe.lunarconcerto.matrixcafe.api.data.response.ProtocolResponse;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBot implements Bot {

    protected BotInfo info;

    protected Adapter adapter;

    @Override
    public @NotNull BotInfo info() {
        return info;
    }

    @Override
    public @NotNull Adapter adapter() {
        return adapter;
    }


}
