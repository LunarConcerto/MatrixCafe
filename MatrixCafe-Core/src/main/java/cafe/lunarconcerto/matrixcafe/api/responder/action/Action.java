package cafe.lunarconcerto.matrixcafe.api.responder.action;

public interface Action<Return, Param extends ActionParam> {

    Return doAction(Param param);

}
