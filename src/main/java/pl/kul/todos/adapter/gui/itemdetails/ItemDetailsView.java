package pl.kul.todos.adapter.gui.itemdetails;

public interface ItemDetailsView {
    void setPresenter(ItemDetailsPresenter presenter);

    void showItemDetails(ItemDetailsDto itemDetailsDto);
}
