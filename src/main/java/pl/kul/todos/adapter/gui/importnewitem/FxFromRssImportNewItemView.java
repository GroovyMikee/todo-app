package pl.kul.todos.adapter.gui.importnewitem;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

class FxFromRssImportNewItemView {
    private FromRssImportNewItemPresenter presenter;

    public void setPresenter(FromRssImportNewItemPresenter presenter) {
        this.presenter = presenter;
    }

    public void showImporter() {
        TextField rssUrlTextField = new TextField();

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
        content.addRow(0, new Label("RSS URL:"), rssUrlTextField);

        Dialog<NewRssSourceDto> itemDialog = new Dialog<>();
        itemDialog.setTitle("Importer elementów");
        itemDialog.getDialogPane().setContent(content);
        itemDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        itemDialog.setResultConverter(buttonPressed -> buttonPressed == ButtonType.OK
                ? new NewRssSourceDto(rssUrlTextField.getText())
                : null);

        itemDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            String errorMessage = null;

            if (rssUrlTextField.getText() == null || rssUrlTextField.getText().isBlank()) {
                errorMessage = "URL nie może być pusty";
            }

            if (errorMessage != null) {
                event.consume();

                new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK)
                        .showAndWait();
            }
        });

        itemDialog.showAndWait()
                .ifPresent(newRssSourceDto -> presenter.importNewRssSource(newRssSourceDto));
    }

    public void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}
