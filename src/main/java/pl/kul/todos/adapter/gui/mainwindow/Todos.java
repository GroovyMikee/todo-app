package pl.kul.todos.adapter.gui.mainwindow;

import pl.kul.todos.adapter.gui.edititem.UpdateItemDto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Todos {
    private final Map<UUID, ItemDto> items;

    public Todos(List<ItemDto> items) {
        this.items = items.stream()
                .collect(
                        () -> new LinkedHashMap<UUID, ItemDto>(),
                        (map, itemDto) -> map.put(itemDto.getId(), itemDto),
                        (map1, map2) -> map1.putAll(map2)
                );
    }

    public List<ItemDto> getItems() {
        return items.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateItem(UUID id, UpdateItemDto updated) {
        ItemDto itemToUpdate = Optional.ofNullable(items.get(id))
                .orElseThrow(() -> new IllegalStateException(String.format("There is no item with id of %s", id)));

        ItemDto updatedItem = new ItemDto(itemToUpdate.getId(), updated.getTitle(), itemToUpdate.getDescription(), itemToUpdate.isDone(), itemToUpdate.getSummary());

        items.put(id, updatedItem);
    }

    public void addItem(ItemDto newItem) {
        items.put(newItem.getId(), newItem);
    }

    public void removeItems(Set<UUID> selectedItemDtos) {
        selectedItemDtos.forEach(id -> items.remove(id));
    }

    public void markAsDone(UUID id, String summary) {
        if (summary == null) {
            throw new NullPointerException("Summary cannot be null");
        } else if (summary.isBlank()) {
            throw new IllegalArgumentException("Summary cannot be blank");
        }

        ItemDto itemToUpdate = Optional.ofNullable(items.get(id))
                .orElseThrow(() -> new IllegalStateException(String.format("There is no item with id of %s", id)));

        ItemDto newItem = new ItemDto(itemToUpdate.getId(), itemToUpdate.getName(), itemToUpdate.getDescription(), true, summary);

        items.put(id, newItem);
    }

    public ItemDto getItem(UUID id) {
        return Optional.ofNullable(items.get(id))
                .orElseThrow(() -> new IllegalStateException(String.format("There is no item with id of %s", id)));
    }

    public void addItems(List<ItemDto> items) {
        items.forEach(itemDto -> addItem(itemDto));
    }
}

