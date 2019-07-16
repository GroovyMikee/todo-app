package pl.kul.todos.adapter.gui.mainwindow;

import java.util.UUID;

public class ItemDto {
    private final UUID id;
    private final String name;
    private final String description;
    private final boolean done;
    private final String summary;

    public ItemDto(UUID id, String name, String description, boolean done, String summary) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.done = done;
        this.summary = summary;
    }

    public ItemDto(String name) {
        this(name, null);
    }

    public ItemDto(String name, String description) {
        this(
                UUID.randomUUID(),
                name,
                description,
                false,
                null
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public String getSummary() {
        return summary;
    }
}
