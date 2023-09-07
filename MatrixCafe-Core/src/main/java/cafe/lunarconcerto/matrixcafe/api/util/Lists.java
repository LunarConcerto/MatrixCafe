package cafe.lunarconcerto.matrixcafe.api.util;

import java.util.List;

public final class Lists {

    private Lists() { }

    public static boolean isValidList(List<?> list){
        return list != null && !list.isEmpty() ;
    }

}
