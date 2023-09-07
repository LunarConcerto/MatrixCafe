package cafe.lunarconcerto.matrixcafe.api.protocol;

import cafe.lunarconcerto.matrixcafe.api.common.MessageResolver;
import cafe.lunarconcerto.matrixcafe.api.data.info.AdapterInfo;

import java.util.Collection;

/**
 * @author LunarConcerto
 * @time 2023/8/17
 */
public class EmptyAdapter extends AbstractAdapter {


    @Override
    public void setup(MessageResolver resolver) { }

    @Override
    public AdapterInfo info() {
        return new AdapterInfo("empty");
    }

    @Override
    public Collection<Bot> createBot() {
        return null;
    }



}
