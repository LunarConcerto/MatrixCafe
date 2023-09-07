package cafe.lunarconcerto.matrixcafe.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.stream.IntStream;

public final class Randoms {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private static final Random RANDOM = new Random();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Randoms() { }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * 生成给定位数的随机数字+字母序列
     * @param length 长度
     * @return 给定长度的随机字母和数字组成的序列
     */
    public static @NotNull String generateSerial(int length){
        StringBuilder builder = new StringBuilder();
        IntStream.range(0, length)
                .map(i -> RANDOM.nextInt(Strings.CHARACTERS.length()))
                .forEach(index -> {
                    char randomChar = Strings.CHARACTERS.charAt(index);
                    builder.append(randomChar);
                });
        return builder.toString();
    }

    /**
     * 生成四位的随机 字母+数字 序列号 / 标识符
     * @return 四位由随机字母和数字组成的序列
     */
    public static @NotNull String generate4DigitSerial() {
        return generateSerial(4);
    }

}
