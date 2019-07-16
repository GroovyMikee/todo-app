package pl.kul.todos.adapter.gui.edititem;

import java.util.UUID;

public class ItemToEditDto {
    private final UUID id;
    private final String title;

    public ItemToEditDto(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
