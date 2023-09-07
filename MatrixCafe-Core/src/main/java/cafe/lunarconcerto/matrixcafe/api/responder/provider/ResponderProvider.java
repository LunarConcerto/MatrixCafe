package cafe.lunarconcerto.matrixcafe.api.responder.provider;

import cafe.lunarconcerto.matrixcafe.api.extension.Extension;

/**
 * 声明一个响应器的提供者, 系统依该类提供的带有{@link cafe.lunarconcerto.matrixcafe.api.responder.provider.annotations.Responds}注解的方法生成响应器.
 */
public abstract class ResponderProvider implements Extension {

    protected void init(){ }

}
