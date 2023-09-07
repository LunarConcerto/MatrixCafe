package cafe.lunarconcerto.matrixcafe.api.responder.fullmatch;

import cafe.lunarconcerto.matrixcafe.api.responder.AbstractResponderBuilder;
import cafe.lunarconcerto.matrixcafe.api.responder.action.CommonAction;
import cafe.lunarconcerto.matrixcafe.api.responder.action.TextCommonAction;
import cafe.lunarconcerto.matrixcafe.api.responder.action.TextToMessageContentAction;
import cafe.lunarconcerto.matrixcafe.api.responder.descriptor.ResponderDescriptor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LunarConcerto
 * @time 2023/6/12
 */
public class FullMatchResponderBuilder extends AbstractResponderBuilder {

    protected Set<String> keywords ;

    protected CommonAction action ;

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull FullMatchResponderBuilder builder(String id){
        return new FullMatchResponderBuilder(id);
    }

    private FullMatchResponderBuilder(String id) {
        super(id);
        keywords = new HashSet<>();
    }

    public FullMatchResponderBuilder withDescriptor(ResponderDescriptor descriptor) {
        this.descriptor = descriptor;
        return this;
    }

    public FullMatchResponderBuilder withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public FullMatchResponderBuilder withAction(CommonAction action) {
        this.action = action;
        return this;
    }

    public FullMatchResponderBuilder withAction(TextCommonAction action) {
        this.action = TextToMessageContentAction.of(action);
        return this;
    }

    public FullMatchResponderBuilder withKeys(String... keys){
        keywords.addAll(Arrays.asList(keys));
        return this;
    }

    public FullMatchResponder build(){
        return new FullMatchResponder(this);
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
