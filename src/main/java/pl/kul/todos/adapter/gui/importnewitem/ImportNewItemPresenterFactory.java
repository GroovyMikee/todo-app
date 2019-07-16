package pl.kul.todos.adapter.gui.importnewitem;

import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;
import pl.kul.todos.adapter.parser.FileParserFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportNewItemPresenterFactory {
    private final FileParserFactory fileParserFactory;

    public ImportNewItemPresenterFactory(FileParserFactory fileParserFactory) {
        this.fileParserFactory = fileParserFactory;
    }

    public ImportNewItemPresenter create(MainWindowPresenter mainWindowPresenter, String sourceType) {
        if (sourceType.equals("FILE")) {
            Path initialDirectoryPath = Paths.get(System.getProperty("user.home"));
            FromFileImportNewItemDto empty = new FromFileImportNewItemDto(initialDirectoryPath);

            FromFileImportNewItemView view = new FxFromFileImportNewItemView();
            FromFileImportNewItemPresenter presenter = new FromFileImportNewItemPresenter(fileParserFactory, mainWindowPresenter, view, empty);
            view.setPresenter(presenter);

            return presenter;
        } else if (sourceType.equals("RSS")) {
            FxFromRssImportNewItemView view = new FxFromRssImportNewItemView();
            FromRssImportNewItemPresenter presenter = new FromRssImportNewItemPresenter(mainWindowPresenter, view);
            view.setPresenter(presenter);

            return presenter;
        } else {
            throw new UnsupportedOperationException("Unsupported importer type " + sourceType);
        }
    }
}
