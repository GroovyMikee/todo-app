package pl.kul.todos.adapter.gui.newitem;

import pl.kul.todos.adapter.gui.mainwindow.ItemDto;

class NewItemDto {
    private final String title;

    public NewItemDto(String title) {
        this.title = title;
    }

    public ItemDto toItem() {
        return new ItemDto(title);
    }
}
