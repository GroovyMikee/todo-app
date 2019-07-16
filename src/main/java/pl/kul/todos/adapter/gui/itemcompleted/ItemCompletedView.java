package pl.kul.todos.adapter.gui.itemcompleted;

import pl.kul.todos.adapter.gui.mainwindow.ItemDto;

public interface ItemCompletedView {
    void setPresenter(ItemCompletedPresenter presenter);

    void showCreator(ItemDto itemDto);
}
