package pl.kul.todos.adapter.gui.newitem;

import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;

public class NewItemPresenterFactory {
    public NewItemPresenter create(MainWindowPresenter mainWindowPresenter) {
        NewItemView newItemView = new FxNewItemView();
        NewItemPresenter presenter = new NewItemPresenter(newItemView, mainWindowPresenter);
        newItemView.setPresenter(presenter);

        return presenter;
    }
}
