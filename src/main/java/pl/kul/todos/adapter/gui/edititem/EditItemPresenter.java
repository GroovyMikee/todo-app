package pl.kul.todos.adapter.gui.edititem;

import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;

public class EditItemPresenter {
    private final EditItemView editItemView;
    private final MainWindowPresenter mainWindowPresenter;
    private final ItemToEditDto itemToEditDto;

    public EditItemPresenter(MainWindowPresenter mainWindowPresenter, ItemToEditDto itemToEditDto, EditItemView editItemView) {

        this.mainWindowPresenter = mainWindowPresenter;
        this.itemToEditDto = itemToEditDto;
        this.editItemView = editItemView;
    }

    public void showEditItem() {
        editItemView.showEditItem(itemToEditDto);
    }

    public void updateItem(UpdateItemDto updateItemDto) {
        mainWindowPresenter.updateItem(itemToEditDto.getId(), updateItemDto);
    }
}
