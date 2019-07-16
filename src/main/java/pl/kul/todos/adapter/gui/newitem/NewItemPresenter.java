package pl.kul.todos.adapter.gui.newitem;

import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;

public class NewItemPresenter {
    private final NewItemView newItemView;
    private final MainWindowPresenter mainWindowPresenter;

    public NewItemPresenter(NewItemView newItemView, MainWindowPresenter mainWindowPresenter) {
        this.newItemView = newItemView;
        this.mainWindowPresenter = mainWindowPresenter;
    }

    public void showNewItemCreator() {
        newItemView.showCreator();
    }

    public void createNewItem(NewItemDto newItemDto) {
        mainWindowPresenter.addNewItem(newItemDto.toItem());
    }
}
