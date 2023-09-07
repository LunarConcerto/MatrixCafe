package cafe.lunarconcerto.matrixcafe.api.data.session;


import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 会话消息记录
 * <p/>
 * 该类基本上是一个定长队列，
 * 由一个链表实现，但是对外只读，
 * 只能由本包下的其它的类进行元素的增加和删改。
 * <p/>
 * 该队列线程不安全，
 * 仅用于记录单个会话内的部分消息。
 */
public class SessionMessageHistory implements Iterable<Message> {

    protected int maxSize = 10;

    protected LinkedList<Message> messages = new LinkedList<>();

    public SessionMessageHistory() {
    }

    public SessionMessageHistory(int maxSize) {
        this.maxSize = maxSize;
    }

    protected void addFirst(Message message) {
        messages.addFirst(message);

        if (messages.size() > maxSize) {
            messages.removeLast();
        }
    }

    public Message get(int index) {
        return messages.get(index);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @NotNull
    @Override
    public Iterator<Message> iterator() {
        return messages.iterator();
    }
}
