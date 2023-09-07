package cafe.lunarconcerto.matrixcafe.api.data.message;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * 消息队列
 * @author LunarConcerto
 * @time 2023/8/17
 */
public class MessageQueue<T extends Message> {

    @Contract(value = " -> new", pure = true)
    public static <T extends Message> @NotNull MessageQueue<T> create(){
        return new MessageQueue<>();
    }

    private final LinkedList<T> messages;

    public MessageQueue() {
        messages = new LinkedList<>();
    }

    /**
     * 入队
     * <p>
     * 加入一个新的消息进入队尾
     * @param t 目标消息对象
     */
    public void enqueue(T t){
        messages.add(t);
    }

    /**
     *
     * @return
     */
    public T dequeue(){
        return messages.poll();
    }

}
