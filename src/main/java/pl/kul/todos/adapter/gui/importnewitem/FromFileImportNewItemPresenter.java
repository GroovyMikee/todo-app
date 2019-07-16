package pl.kul.todos.adapter.gui.importnewitem;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import pl.kul.todos.adapter.gui.mainwindow.ItemDto;
import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;
import pl.kul.todos.adapter.parser.FileParser;
import pl.kul.todos.adapter.parser.FileParserFactory;
import pl.kul.todos.adapter.parser.Item;

import java.io.File;
import java.util.List;

class FromFileImportNewItemPresenter implements ImportNewItemPresenter {

    private final MainWindowPresenter mainWindowPresenter;
    private final FromFileImportNewItemView fromFileImportNewItemView;
    private final FromFileImportNewItemDto fromFileImportNewItemDto;
    private final FileParser fileParser;

    public FromFileImportNewItemPresenter(
            FileParserFactory fileParserFactory,
            MainWindowPresenter mainWindowPresenter,
            FromFileImportNewItemView fromFileImportNewItemView,
            FromFileImportNewItemDto fromFileImportNewItemDto) {
        this.mainWindowPresenter = mainWindowPresenter;
        this.fromFileImportNewItemView = fromFileImportNewItemView;
        this.fromFileImportNewItemDto = fromFileImportNewItemDto;

        fileParser = fileParserFactory.create();
    }

    @Override
    public void showImporter() {
        fromFileImportNewItemView.showImporter();
    }

    public void importFile() {
        try {
            List<Item> parsedItems = fileParser.parse(fromFileImportNewItemDto.selectedFileProperty().getValue().toPath());

            parsedItems.stream()
                    .map(item -> new ItemDto(item.getDescription()))
                    .forEach(itemDto -> mainWindowPresenter.addNewItem(itemDto));
        } catch (IllegalStateException ex) {
            fromFileImportNewItemView.showImportError(ex.getMessage());
        }
    }

    public ObservableValue<File> initialDirectoryProperty() {
        return fromFileImportNewItemDto.initialDirectoryProperty();
    }

    public Property<File> selectedFileProperty() {
        return fromFileImportNewItemDto.selectedFileProperty();
    }

    public Property<String> selectedFilePathProperty() {
        return fromFileImportNewItemDto.selectedFilePathProperty();
    }
}
