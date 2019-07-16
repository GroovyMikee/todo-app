package pl.kul.todos.adapter.gui.mainwindow;

import pl.kul.todos.adapter.gui.edititem.EditItemPresenterFactory;
import pl.kul.todos.adapter.gui.edititem.ItemToEditDto;
import pl.kul.todos.adapter.gui.edititem.UpdateItemDto;
import pl.kul.todos.adapter.gui.importnewitem.ImportNewItemPresenter;
import pl.kul.todos.adapter.gui.importnewitem.ImportNewItemPresenterFactory;
import pl.kul.todos.adapter.gui.itemcompleted.ItemCompletedPresenterFactory;
import pl.kul.todos.adapter.gui.itemdetails.ItemDetailsPresenterFactory;
import pl.kul.todos.adapter.gui.newitem.NewItemPresenterFactory;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MainWindowPresenter {
    private final Todos todos;
    private final MainWindowView mainWindowView;
    private final NewItemPresenterFactory newItemPresenterFactory;
    private final EditItemPresenterFactory editItemPresenterFactory;
    private final ImportNewItemPresenterFactory importNewItemPresenterFactory;
    private final ItemCompletedPresenterFactory itemCompletedPresenterFactory;
    private final ItemDetailsPresenterFactory itemDetailsPresenterFactory;

    public MainWindowPresenter(
            Todos todos,
            MainWindowView mainWindowView,
            NewItemPresenterFactory newItemPresenterFactory,
            EditItemPresenterFactory editItemPresenterFactory,
            ImportNewItemPresenterFactory importNewItemPresenterFactory,
            ItemCompletedPresenterFactory itemCompletedPresenterFactory,
            ItemDetailsPresenterFactory itemDetailsPresenterFactory) {
        this.todos = todos;
        this.mainWindowView = mainWindowView;
        this.newItemPresenterFactory = newItemPresenterFactory;
        this.editItemPresenterFactory = editItemPresenterFactory;
        this.importNewItemPresenterFactory = importNewItemPresenterFactory;
        this.itemCompletedPresenterFactory = itemCompletedPresenterFactory;
        this.itemDetailsPresenterFactory = itemDetailsPresenterFactory;

        mainWindowView.displayTodoItems(todos.getItems());
    }

    public void addNewItem() {
        newItemPresenterFactory.create(this)
                .showNewItemCreator();
    }

    public void addNewItem(ItemDto newItem) {
        todos.addItem(newItem);
        displayTodoItems(todos.getItems());
    }

    public void editSelectedItem(ItemDto selectedItemDto) {
        ItemToEditDto itemToEditDto = new ItemToEditDto(selectedItemDto.getId(), selectedItemDto.getName());

        editItemPresenterFactory.create(this, itemToEditDto)
                .showEditItem();
    }

    public void removeSelectedItems(Set<UUID> selectedItemDtos) {
        todos.removeItems(selectedItemDtos);
        displayTodoItems(todos.getItems());
    }

    public void updateItem(UUID id, UpdateItemDto updated) {
        todos.updateItem(id, updated);
        displayTodoItems(todos.getItems());
    }

    public void importItems(String sourceType) {
        ImportNewItemPresenter importNewItemPresenter = importNewItemPresenterFactory.create(this, sourceType);
        importNewItemPresenter.showImporter();
    }

    public void markDone(ItemDto itemDto) {
        itemCompletedPresenterFactory.create(this)
                .showItemCompletedCreator(itemDto);
    }

    public void markDone(UUID id, String summary) {
        todos.markAsDone(id, summary);
        displayTodoItems(todos.getItems());
    }

    public void displayTodoItems(List<ItemDto> items) {
        List<ItemDto> sortedItems = items.stream()
                .sorted(new DoneItemsLastComparator())
                .collect(Collectors.toList());

        mainWindowView.displayTodoItems(sortedItems);
    }

    public void showDetails(UUID id) {
        itemDetailsPresenterFactory.create(this)
                .showItemDetails(todos.getItem(id));
    }

    public void addNewItem(List<ItemDto> items) {
        todos.addItems(items);
        displayTodoItems(todos.getItems());
    }
}
