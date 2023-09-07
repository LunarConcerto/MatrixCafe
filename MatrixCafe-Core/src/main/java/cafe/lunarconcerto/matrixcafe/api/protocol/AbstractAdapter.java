package cafe.lunarconcerto.matrixcafe.api.protocol;

import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationManager;
import cafe.lunarconcerto.matrixcafe.api.common.MessageResolver;
import cafe.lunarconcerto.matrixcafe.api.data.info.AdapterInfo;
import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import cafe.lunarconcerto.matrixcafe.api.data.message.MessageQueue;

import javax.inject.Inject;
import java.util.Collection;

public abstract class AbstractAdapter implements Adapter {

    @Inject
    protected MessageResolver resolver ;

    @Inject
    protected ConfigurationManager configurationManager ;

    protected <T extends Message>MessageQueue<T> createQueue(){
        return MessageQueue.create();
    }

}
