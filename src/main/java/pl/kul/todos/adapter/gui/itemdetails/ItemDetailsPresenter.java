package pl.kul.todos.adapter.gui.itemdetails;

import pl.kul.todos.adapter.gui.mainwindow.ItemDto;

public class ItemDetailsPresenter {
    private final ItemDetailsView itemDetailsView;

    public ItemDetailsPresenter(ItemDetailsView itemDetailsView) {
        this.itemDetailsView = itemDetailsView;
    }

    public void showItemDetails(ItemDto itemDto) {
        itemDetailsView.showItemDetails(new ItemDetailsDto(
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.isDone() ? "Ukończony" : "Oczekuje",
                itemDto.getSummary() == null ? "-" : itemDto.getSummary()
        ));
    }
}
