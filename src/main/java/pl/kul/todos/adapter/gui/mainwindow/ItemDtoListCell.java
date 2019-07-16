package pl.kul.todos.adapter.gui.mainwindow;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.util.function.Consumer;

class ItemDtoListCell extends ListCell<ItemDto> {
    private final Consumer<ItemDto> onTickClicked;
    private final Consumer<ItemDto> onTitleClicked;

    public ItemDtoListCell(Consumer<ItemDto> onTickClicked, Consumer<ItemDto> onTitleClicked) {
        this.onTickClicked = onTickClicked;
        this.onTitleClicked = onTitleClicked;
    }

    @Override
    protected void updateItem(ItemDto item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            Text titleText = new Text(item.getName());
            titleText.setStrikethrough(item.isDone());
            titleText.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    onTitleClicked.accept(item);
                }
            });

            Label tickLabel = new Label("\u2713");
            tickLabel.setVisible(!item.isDone());
            tickLabel.setOnMouseClicked(event -> {
                if (!item.isDone()) {
                    onTickClicked.accept(item);
                }
            });

            GridPane itemGrid = new GridPane();
            GridPane.setHgrow(itemGrid, Priority.ALWAYS);
            itemGrid.addRow(
                    itemGrid.getRowCount(),
                    titleText,
                    tickLabel
            );

            ColumnConstraints titleColumn = new ColumnConstraints();
            titleColumn.setHgrow(Priority.ALWAYS);
            titleColumn.setHalignment(HPos.LEFT);

            ColumnConstraints buttonsColumn = new ColumnConstraints();
            buttonsColumn.setHalignment(HPos.RIGHT);

            itemGrid.getColumnConstraints()
                    .addAll(titleColumn, buttonsColumn);

            setGraphic(itemGrid);
        }
    }
}
