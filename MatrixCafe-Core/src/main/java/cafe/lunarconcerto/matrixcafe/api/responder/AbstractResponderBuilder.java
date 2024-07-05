package cafe.lunarconcerto.matrixcafe.api.responder;

import cafe.lunarconcerto.matrixcafe.api.responder.descriptor.ResponderDescriptor;

/**
 * @author LunarConcerto
 * @time 2023/6/21
 */
public abstract class AbstractResponderBuilder {

    protected String id ;

    protected ResponderDescriptor descriptor;

    protected int priority = 0;

    public AbstractResponderBuilder(String id) {
        this.id = id;
    }

    public AbstractResponderBuilder withDescriptor(ResponderDescriptor descriptor) {
        this.descriptor = descriptor;
        return this;
    }

    public AbstractResponderBuilder withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    protected String getId() {
        return id;
    }

    protected ResponderDescriptor getDescriptor() {
        return descriptor;
    }

    protected int getPriority() {
        return priority;
    }

}
