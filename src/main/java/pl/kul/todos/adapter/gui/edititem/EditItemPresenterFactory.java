package pl.kul.todos.adapter.gui.edititem;

import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;

public class EditItemPresenterFactory {
    public EditItemPresenter create(MainWindowPresenter mainWindowPresenter, ItemToEditDto itemToEditDto) {
        EditItemView editItemView = new FxEditItemView();
        EditItemPresenter editItemPresenter = new EditItemPresenter(mainWindowPresenter, itemToEditDto, editItemView);

        editItemView.setPresenter(editItemPresenter);

        return editItemPresenter;
    }
}
