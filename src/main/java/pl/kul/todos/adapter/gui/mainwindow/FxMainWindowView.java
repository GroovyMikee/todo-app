package pl.kul.todos.adapter.gui.mainwindow;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class FxMainWindowView implements MainWindowView {
    private final ListView<ItemDto> itemListView;

    private MainWindowPresenter presenter;

    public FxMainWindowView(Pane parent) {
        itemListView = new ListView<>();
        itemListView.setMinHeight(200);
        itemListView.setCellFactory(new Callback<ListView<ItemDto>, ListCell<ItemDto>>() {
            @Override
            public ListCell<ItemDto> call(ListView<ItemDto> param) {
                return new ItemDtoListCell(
                        itemDto -> presenter.markDone(itemDto),
                        clickedItemDto -> presenter.showDetails(clickedItemDto.getId())
                );
            }
        });

        MenuItem importFromFileMenuItem = new MenuItem("Z pliku");
        importFromFileMenuItem.setOnAction(event -> presenter.importItems("FILE"));

        MenuItem importFromRssMenuItem = new MenuItem("Z kanału RSS");
        importFromRssMenuItem.setOnAction(event -> presenter.importItems("RSS"));

        MenuButton importItemsButton = new MenuButton(
                "\uD83D\uDCC2",
                null,
                importFromFileMenuItem,
                importFromRssMenuItem
        );
        Button addItemButton = new Button("\u002b");
        Button editItemButton = new Button("\u270E");
        Button removeItemButton = new Button("\u2672");

        itemListView.getSelectionModel()
                .getSelectedItems()
                .addListener((ListChangeListener<ItemDto>) c -> {
                    ObservableList<ItemDto> selectedItemDtos = itemListView.getSelectionModel().getSelectedItems();

                    editItemButton.disableProperty().setValue(selectedItemDtos.size() != 1);
                    removeItemButton.disableProperty().setValue(selectedItemDtos.isEmpty());
                });

        editItemButton.setDisable(true);
        removeItemButton.setDisable(true);

        addItemButton.setOnAction(event -> presenter.addNewItem());
        editItemButton.setOnAction(event -> presenter.editSelectedItem(itemListView.getSelectionModel().getSelectedItem()));
        removeItemButton.setOnAction(event -> {
            Set<UUID> selectedItems = itemListView.getSelectionModel().getSelectedItems()
                    .stream()
                    .map(itemDto -> itemDto.getId())
                    .collect(Collectors.toSet());
            presenter.removeSelectedItems(selectedItems);
        });

        HBox actionButtons = new HBox();
        actionButtons.setPadding(new Insets(5));
        actionButtons.setSpacing(5);
        actionButtons.setAlignment(Pos.BASELINE_RIGHT);
        actionButtons.getChildren().addAll(
                importItemsButton,
                addItemButton,
                editItemButton,
                removeItemButton
        );

        VBox layout = new VBox();
        VBox.setVgrow(layout, Priority.ALWAYS);
        layout.setPadding(new Insets(5));
        layout.setSpacing(5);
        layout.getChildren().addAll(
                itemListView,
                actionButtons
        );

        itemListView.prefHeightProperty()
                .bind(layout.heightProperty().subtract(actionButtons.heightProperty()));

        parent.getChildren().add(layout);
    }

    @Override
    public void setPresenter(MainWindowPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayTodoItems(List<ItemDto> todos) {
        itemListView.setItems(FXCollections.observableList(todos));
    }
}
