package cafe.lunarconcerto.matrixcafe.api.responder.impl.regex;

import cafe.lunarconcerto.matrixcafe.api.responder.AbstractResponderBuilder;
import cafe.lunarconcerto.matrixcafe.api.responder.action.CommonAction;
import cafe.lunarconcerto.matrixcafe.api.responder.descriptor.ResponderDescriptor;

/**
 * @author LunarConcerto
 * @time 2023/6/21
 */
public class RegexResponderBuilder extends AbstractResponderBuilder {

    protected RegexMatcher matcher ;

    protected CommonAction action ;

    public RegexResponderBuilder(String id) {
        super(id);
    }

    @Override
    public RegexResponderBuilder withDescriptor(ResponderDescriptor descriptor) {
        super.withDescriptor(descriptor);
        return this;
    }

    @Override
    public RegexResponderBuilder withPriority(int priority) {
        super.withPriority(priority);
        return this;
    }

    public RegexResponderBuilder withMatcher(RegexMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

    public RegexResponderBuilder withAction(CommonAction action) {
        this.action = action;
        return this;
    }

    public RegexResponder build(){
        return new RegexResponder(this);
    }

    @Override
    protected String getId() {
        return super.getId();
    }

    @Override
    protected ResponderDescriptor getDescriptor() {
        return super.getDescriptor();
    }

    @Override
    protected int getPriority() {
        return super.getPriority();
    }
}
