package cafe.lunarconcerto.matrixcafe.api.responder.action;

public interface Action<Return, Data extends ActionData> {

    Return start(Data data);

}
