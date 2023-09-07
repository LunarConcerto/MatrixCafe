package cafe.lunarconcerto.matrixcafe.api.plugin.mvn;

import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginClassContainer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
final class RequirementDependencyPluginWrapper {

    private PluginClassContainer container ;

    private String[] libraries ;

    private List<File> libraryFiles ;

    public RequirementDependencyPluginWrapper(@NotNull PluginClassContainer container) {
        this.container = container;
        libraries = container.getLibraries() ;
        libraryFiles = new LinkedList<>();
    }

    public boolean addAll(@NotNull Collection<? extends File> c) {
        return libraryFiles.addAll(c);
    }

    public boolean add(File file) {
        return libraryFiles.add(file);
    }

    public void load(){
        for (File file : libraryFiles) {
            container.addDependencyFile(file);
        }
    }

}
