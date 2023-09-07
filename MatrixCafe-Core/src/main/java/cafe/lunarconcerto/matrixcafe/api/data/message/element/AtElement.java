package cafe.lunarconcerto.matrixcafe.api.data.message.element;

public class AtElement implements Element{

    /**
     * 被 at 人的ID
     */
    protected String id ;

    /**
     * 是否是机器人被 At
     */
    protected boolean isAtSelf ;

    public AtElement(String id) {
        this.id = id;
    }

    public AtElement(String id, boolean isAtSelf) {
        this.id = id;
        this.isAtSelf = isAtSelf;
    }

}
