package cafe.lunarconcerto.matrixcafe.api.responder.provider.annotations;

import cafe.lunarconcerto.matrixcafe.api.util.Strings;

public @interface Command {

    /**
     *
     *
     * <pre>
     *     例:
     *     {@code
     *     @Command( pattern = "foo {a} ")
     *     public void fooAction(String a){
     *         System.out.println(a);
     *     }
     *     }
     * </pre>
     */
    String pattern();

    String description() default Strings.EMPTY ;


}
