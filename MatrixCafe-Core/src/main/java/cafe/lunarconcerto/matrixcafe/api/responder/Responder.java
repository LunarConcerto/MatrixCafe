package cafe.lunarconcerto.matrixcafe.api.responder;

import cafe.lunarconcerto.matrixcafe.api.data.message.BotMessage;
import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.response.Response;
import cafe.lunarconcerto.matrixcafe.api.responder.action.ActionParam;
import cafe.lunarconcerto.matrixcafe.api.responder.descriptor.ResponderDescriptor;
import cafe.lunarconcerto.matrixcafe.api.responder.descriptor.TextDescriptor;
import cafe.lunarconcerto.matrixcafe.api.util.Randoms;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * <h1>响应器</h1>
 * 响应器是消息响应的核心,
 * MatrixCafe在接收到消息后将轮流分发给每个响应器进行确认
 */
public abstract class Responder implements Comparable<Responder>{

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected final String id ;

    protected ResponderDescriptor descriptor = TextDescriptor.DEFAULT_DESCRIPTOR;

    /**
     * 响应器的优先级,
     * 优先级不仅决定响应器的通过顺序,
     * 而且决定当有多个响应器可以响应时的选用.
     */
    protected int priority = 0;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public Responder(String id) {
        this.id = generateId(id);
    }

    public Responder(String id, ResponderDescriptor descriptor, int priority) {
        this.id = generateId(id);
        this.descriptor = descriptor;
        this.priority = priority;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public abstract ResponderGroup<?> createGroup();

    /**
     * 触发响应器的动作
     * @param data 收到的消息
     * @return 响应结果
     */
    public abstract Response<?> respond(ActionParam data);

    protected Response<?> sendReply(MessageContent content, @NotNull SessionMessage message){
        return Response.okResponse(message.getBot().send(new BotMessage(message, content)));
    }

    private @NotNull String generateId(String id){
        return id + "#" + Randoms.generate4DigitSerial();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Getter / Setter
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public Responder setDescriptor(ResponderDescriptor descriptor) {
        this.descriptor = descriptor;
        return this;
    }

    public ResponderDescriptor getDescriptor() {
        return descriptor;
    }

    public int getPriority(){
        return priority ;
    }

    public Responder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public String getId() {
        return id;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Override Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public int compareTo(@NotNull Responder o){
        return o.getPriority() - getPriority();
    }

    public static class ResponderComparator implements Comparator<Responder> {

        @Override
        public int compare(@NotNull Responder o1, @NotNull Responder o2) {
            return o1.getPriority() - o2.getPriority();
        }

    }

}
