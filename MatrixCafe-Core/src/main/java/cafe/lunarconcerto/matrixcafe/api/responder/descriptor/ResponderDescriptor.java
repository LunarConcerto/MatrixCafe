package cafe.lunarconcerto.matrixcafe.api.responder.descriptor;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;

/**
 * 响应器的说明获取
 */
public interface ResponderDescriptor {

    MessageContent description();

    MessageContent example();

}
