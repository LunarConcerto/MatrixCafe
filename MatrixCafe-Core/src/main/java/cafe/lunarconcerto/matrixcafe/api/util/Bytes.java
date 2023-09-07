package cafe.lunarconcerto.matrixcafe.api.util;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public final class Bytes {

    private Bytes() { }

    public static byte @NotNull [] readFrom(File file) throws IOException {
        try (
                FileInputStream inputStream = new FileInputStream(file);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ){
            byte[] bytes = new byte[1024];
            int temp ;

            while ((temp = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, temp);
            }

            return outputStream.toByteArray();
        }
    }

}
