package cafe.lunarconcerto.matrixcafe.api.data.message.element;

public class AtElement implements Element{

    /**
     * 被 at 人的信息
     */
    protected String atWho;

    /**
     * 是否是机器人被 At
     */
    protected boolean isAtSelf ;

    public AtElement(String atWho) {
        this.atWho = atWho;
    }

    public AtElement(String atWho, boolean isAtSelf) {
        this.atWho = atWho;
        this.isAtSelf = isAtSelf;
    }

}
