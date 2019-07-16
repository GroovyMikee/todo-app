package pl.kul.todos.adapter.gui.itemcompleted;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.kul.todos.adapter.gui.mainwindow.ItemDto;

class FxItemCompletedView implements ItemCompletedView {
    private ItemCompletedPresenter presenter;

    @Override
    public void setPresenter(ItemCompletedPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCreator(ItemDto itemDto) {
        TextArea summaryText = new TextArea();
        summaryText.setPrefSize(300, 50);

        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setMinWidth(ColumnConstraints.CONSTRAIN_TO_PREF);
        leftColumn.setHalignment(HPos.RIGHT);

        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setHgrow(Priority.ALWAYS);

        GridPane content = new GridPane();
        content.getColumnConstraints().addAll(leftColumn, rightColumn);
        content.setPadding(new Insets(5));
        content.setHgap(5);
        content.setVgap(5);
        VBox summaryLabelLayout = new VBox(new Label("Summary:"));
        summaryLabelLayout.setAlignment(Pos.TOP_RIGHT);
        content.addRow(0, summaryLabelLayout, summaryText);

        Dialog<ItemCompletedDto> itemDialog = new Dialog<>();
        itemDialog.setTitle("Finish the task");
        itemDialog.getDialogPane().setContent(content);
        itemDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        itemDialog.setResultConverter(buttonPressed -> buttonPressed == ButtonType.OK
                ? new ItemCompletedDto(itemDto.getId(), summaryText.getText())
                : null);

        itemDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            String errorMessage = null;

            if (summaryText.getText() == null || summaryText.getText().isBlank()) {
                errorMessage = "Complete the summary";
            }

            if (errorMessage != null) {
                event.consume();

                new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK)
                        .showAndWait();
            }
        });

        itemDialog.showAndWait()
                .ifPresent(itemCompletedDto -> presenter.markItemAsComplete(itemCompletedDto));
    }
}
