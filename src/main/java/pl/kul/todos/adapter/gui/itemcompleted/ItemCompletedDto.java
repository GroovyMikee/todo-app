package pl.kul.todos.adapter.gui.itemcompleted;

import java.util.UUID;

class ItemCompletedDto {
    private final UUID id;
    private final String summary;

    public ItemCompletedDto(UUID id, String summary) {
        this.id = id;
        this.summary = summary;
    }

    public UUID getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }
}
