package cafe.lunarconcerto.matrixcafe.api.responder.action;

public interface TextCommonAction extends Action<String, ActionData>{

    @Override
    String start(ActionData message);

}
