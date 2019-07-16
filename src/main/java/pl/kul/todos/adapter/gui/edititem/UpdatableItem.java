package pl.kul.todos.adapter.gui.edititem;

import pl.kul.todos.adapter.gui.mainwindow.ItemDto;

public interface UpdatableItem {
    void updateItem(ItemDto original, ItemDto updated);
}
