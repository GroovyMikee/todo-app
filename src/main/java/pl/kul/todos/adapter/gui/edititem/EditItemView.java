package pl.kul.todos.adapter.gui.edititem;

public interface EditItemView {
    void setPresenter(EditItemPresenter editItemPresenter);

    void showEditItem(ItemToEditDto item);
}
