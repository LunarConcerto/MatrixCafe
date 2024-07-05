package cafe.lunarconcerto.matrixcafe.api.responder.action;

public interface TextCommonAction extends Action<String, ActionParam>{

    @Override
    String doAction(ActionParam message);

}
