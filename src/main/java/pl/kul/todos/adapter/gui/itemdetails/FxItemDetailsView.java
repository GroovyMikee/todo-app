package pl.kul.todos.adapter.gui.itemdetails;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

class FxItemDetailsView implements ItemDetailsView {
    private final DescriptionConverter descriptionConverter;
    
    private ItemDetailsPresenter presenter;

    public FxItemDetailsView(DescriptionConverter descriptionConverter) {
        this.descriptionConverter = descriptionConverter;
    }

    @Override
    public void setPresenter(ItemDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showItemDetails(ItemDetailsDto itemDetailsDto) {
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setMinWidth(ColumnConstraints.CONSTRAIN_TO_PREF);
        leftColumn.setHalignment(HPos.RIGHT);

        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setHgrow(Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMinWidth(450);
        content.getColumnConstraints().addAll(leftColumn, rightColumn);
        content.setPadding(new Insets(5));
        content.setHgap(5);
        content.setVgap(5);
        content.addRow(0, new Label("Title:"), new Label(itemDetailsDto.getTitle()));
        content.addRow(1, new Label("Description:"), descriptionConverter.toTextFlow(itemDetailsDto.getDescription()).mapToComponent());
        content.addRow(2, new Label("State:"), new Label(itemDetailsDto.getStatus()));
        VBox summaryLabelLayout = new VBox(new Label("Recapitulation:"));
        summaryLabelLayout.setAlignment(Pos.TOP_RIGHT);
        content.addRow(3, summaryLabelLayout, new Label(itemDetailsDto.getSummary()));

        Dialog<ItemDetailsDto> itemDialog = new Dialog<>();
        itemDialog.setTitle("Task details");
        itemDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        itemDialog.getDialogPane().setContent(content);

        itemDialog.show();
    }
}
