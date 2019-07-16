package pl.kul.todos.adapter.gui.importnewitem;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

import java.io.File;

class FxFromFileImportNewItemView implements FromFileImportNewItemView {
    private FromFileImportNewItemPresenter presenter;

    @Override
    public void setPresenter(FromFileImportNewItemPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showImporter() {
        TextField filePath = new TextField();
        filePath.setDisable(true);

        Button selectFileButton = new Button("Wybierz plik");

        FileChooser fileChooser = new FileChooser();
        fileChooser.initialDirectoryProperty().bind(presenter.initialDirectoryProperty());
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV file", "*.csv"));

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
        content.addRow(content.getRowCount(), filePath, selectFileButton);

        Dialog<ButtonType> itemDialog = new Dialog<>();
        itemDialog.setTitle("Importer elementów");
        itemDialog.getDialogPane().setContent(content);
        itemDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        filePath.textProperty().bindBidirectional(presenter.selectedFilePathProperty());

        selectFileButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(itemDialog.getOwner());
            presenter.selectedFileProperty().setValue(selectedFile);
        });

        itemDialog.showAndWait()
                .filter(ButtonType.OK::equals)
                .ifPresent(buttonType -> presenter.importFile());
    }

    @Override
    public void showImportError(String message) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR, String.format("Import nie powiódł się z powodu:\n\n%s", message));
        errorDialog.setTitle("Importu elementów");
        errorDialog.showAndWait();
    }
}
