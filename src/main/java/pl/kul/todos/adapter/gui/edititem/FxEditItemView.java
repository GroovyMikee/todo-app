package pl.kul.todos.adapter.gui.edititem;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FxEditItemView implements EditItemView {
    private EditItemPresenter editItemPresenter;

    @Override
    public void setPresenter(EditItemPresenter editItemPresenter) {
        this.editItemPresenter = editItemPresenter;
    }

    @Override
    public void showEditItem(ItemToEditDto item) {
        TextField itemTitleTextField = new TextField(item.getTitle());

        GridPane content = new GridPane();
        content.setPadding(new Insets(5));
        content.setHgap(5);
        content.setVgap(5);
        content.addRow(0, new Label("Tytuł:"), itemTitleTextField);

        Dialog<UpdateItemDto> itemDialog = new Dialog<>();
        itemDialog.setTitle("Edycja elementu");
        itemDialog.getDialogPane().setContent(content);
        itemDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        itemDialog.setResultConverter(buttonPressed -> buttonPressed == ButtonType.OK
                ? new UpdateItemDto(itemTitleTextField.getText())
                : null);

        itemDialog.showAndWait()
                .ifPresent(updateItemDto -> editItemPresenter.updateItem(updateItemDto));
    }
}
