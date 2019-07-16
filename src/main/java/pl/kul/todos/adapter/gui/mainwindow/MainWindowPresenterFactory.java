package pl.kul.todos.adapter.gui.mainwindow;

import javafx.scene.layout.Pane;
import pl.kul.todos.adapter.gui.edititem.EditItemPresenterFactory;
import pl.kul.todos.adapter.gui.importnewitem.ImportNewItemPresenterFactory;
import pl.kul.todos.adapter.gui.itemcompleted.ItemCompletedPresenterFactory;
import pl.kul.todos.adapter.gui.itemdetails.ItemDetailsPresenterFactory;
import pl.kul.todos.adapter.gui.newitem.NewItemPresenterFactory;

public class MainWindowPresenterFactory {
    private final Pane rootLayout;
    private final NewItemPresenterFactory newItemPresenterFactory;
    private final EditItemPresenterFactory editItemPresenterFactory;
    private final ImportNewItemPresenterFactory importNewItemPresenterFactory;
    private final ItemCompletedPresenterFactory itemCompletedPresenterFactory;
    private final ItemDetailsPresenterFactory itemDetailsPresenterFactory;

    public MainWindowPresenterFactory(Pane rootLayout, NewItemPresenterFactory newItemPresenterFactory, EditItemPresenterFactory editItemPresenterFactory, ImportNewItemPresenterFactory importNewItemPresenterFactory, ItemCompletedPresenterFactory itemCompletedPresenterFactory, ItemDetailsPresenterFactory itemDetailsPresenterFactory) {
        this.rootLayout = rootLayout;
        this.newItemPresenterFactory = newItemPresenterFactory;
        this.editItemPresenterFactory = editItemPresenterFactory;
        this.importNewItemPresenterFactory = importNewItemPresenterFactory;
        this.itemCompletedPresenterFactory = itemCompletedPresenterFactory;
        this.itemDetailsPresenterFactory = itemDetailsPresenterFactory;
    }

    public MainWindowPresenter create(Todos todos) {
        MainWindowView mainWindowView = new FxMainWindowView(rootLayout);
        MainWindowPresenter presenter = new MainWindowPresenter(todos, mainWindowView, newItemPresenterFactory, editItemPresenterFactory, importNewItemPresenterFactory, itemCompletedPresenterFactory, itemDetailsPresenterFactory);
        mainWindowView.setPresenter(presenter);

        return presenter;
    }
}
