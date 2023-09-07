package cafe.lunarconcerto.matrixcafe.api.data.message.element;

import java.io.File;

/**
 * @author LunarConcerto
 * @time 2023/5/19
 */
public class FileElement implements Element{

    protected File file ;

    public FileElement(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
