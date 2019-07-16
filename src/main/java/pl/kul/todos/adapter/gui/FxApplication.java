package pl.kul.todos.adapter.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.kul.todos.adapter.gui.edititem.EditItemPresenterFactory;
import pl.kul.todos.adapter.gui.importnewitem.ImportNewItemPresenterFactory;
import pl.kul.todos.adapter.gui.itemcompleted.ItemCompletedPresenterFactory;
import pl.kul.todos.adapter.gui.itemdetails.ItemDetailsPresenterFactory;
import pl.kul.todos.adapter.gui.mainwindow.ItemDto;
import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenterFactory;
import pl.kul.todos.adapter.gui.mainwindow.Todos;
import pl.kul.todos.adapter.gui.newitem.NewItemPresenterFactory;
import pl.kul.todos.adapter.parser.FileParserFactory;

import java.util.LinkedList;
import java.util.List;

public class FxApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane rootLayout = new VBox();
        Scene scene = new Scene(rootLayout);

        List<ItemDto> items = new LinkedList<>(List.of(
                new ItemDto("First item"),
                new ItemDto("Odrób pracę domową"),
                new ItemDto("Napisz licencjat")
        ));

        // Dependencies
        FileParserFactory fileParserFactory = new FileParserFactory();

        // Model
        Todos todos = new Todos(items);

        // Presenters & views
        MainWindowPresenterFactory mainWindowPresenterFactory = new MainWindowPresenterFactory(
                rootLayout,
                new NewItemPresenterFactory(),
                new EditItemPresenterFactory(),
                new ImportNewItemPresenterFactory(fileParserFactory),
                new ItemCompletedPresenterFactory(),
                new ItemDetailsPresenterFactory(getHostServices())
        );

        mainWindowPresenterFactory.create(todos);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
