package pl.kul.todos.adapter.gui.importnewitem;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.StringConverter;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

class FromFileImportNewItemDto {
    private final SimpleObjectProperty<File> initialDirectory;
    private final SimpleObjectProperty<File> selectedFile;
    private final Property<String> selectedFilePath;

    public FromFileImportNewItemDto(Path initialDirectory) {
        if (!initialDirectory.toFile().isDirectory()) {
            throw new IllegalArgumentException("Path of directory is expected to be passed as initial directory");
        }

        this.initialDirectory = new SimpleObjectProperty<>(initialDirectory.toFile());
        selectedFile = new SimpleObjectProperty<>();
        selectedFilePath = new SimpleStringProperty();

        Bindings.bindBidirectional(selectedFilePath, selectedFile, new StringConverter<File>() {
            @Override
            public String toString(File object) {
                return Optional.ofNullable(object).map(File::toString).orElse("");
            }

            @Override
            public File fromString(String string) {
                return new File(string);
            }
        });
    }

    public ObservableValue<File> initialDirectoryProperty() {
        return initialDirectory;
    }

    public Property<File> selectedFileProperty() {
        return selectedFile;
    }

    public Property<String> selectedFilePathProperty() {
        return selectedFilePath;
    }
}
