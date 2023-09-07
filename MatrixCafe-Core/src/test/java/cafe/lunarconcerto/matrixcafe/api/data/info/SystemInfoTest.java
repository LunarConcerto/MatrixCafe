package cafe.lunarconcerto.matrixcafe.api.data.info;

import org.junit.jupiter.api.Test;

/**
 * @author LunarConcerto
 * @time 2023/7/26
 */
class SystemInfoTest {

    @Test
    void getInfoTest() {
        SystemInfo info = SystemInfo.create();

        System.out.println(info.getJvmName());
        System.out.println(info.getJvmVersion());
        System.out.println(info.getJvmArgument());

    }
}