package cafe.lunarconcerto.matrixcafe.api.data.message.element;

import java.io.File;

/**
 * @author LunarConcerto
 * @time 2023/5/19
 */
public class ImageElement implements Element{

    protected File sourceFile ;

    protected String sourceUrl ;

    public ImageElement(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public ImageElement(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public ImageElement(File sourceFile, String sourceUrl) {
        this.sourceFile = sourceFile;
        this.sourceUrl = sourceUrl;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
}
