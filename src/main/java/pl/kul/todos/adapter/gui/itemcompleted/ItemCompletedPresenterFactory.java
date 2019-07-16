package pl.kul.todos.adapter.gui.itemcompleted;

import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;

public class ItemCompletedPresenterFactory {
    public ItemCompletedPresenter create(MainWindowPresenter mainWindowPresenter) {
        ItemCompletedView itemCompletedView = new FxItemCompletedView();
        ItemCompletedPresenter presenter = new ItemCompletedPresenter(itemCompletedView, mainWindowPresenter);
        itemCompletedView.setPresenter(presenter);

        return presenter;
    }
}
