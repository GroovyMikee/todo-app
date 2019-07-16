package pl.kul.todos.adapter.gui.itemcompleted;

import pl.kul.todos.adapter.gui.mainwindow.ItemDto;
import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;

public class ItemCompletedPresenter {
    private final ItemCompletedView itemCompletedView;
    private final MainWindowPresenter mainWindowPresenter;

    public ItemCompletedPresenter(ItemCompletedView itemCompletedView, MainWindowPresenter mainWindowPresenter) {
        this.itemCompletedView = itemCompletedView;
        this.mainWindowPresenter = mainWindowPresenter;
    }

    public void showItemCompletedCreator(ItemDto itemDto) {
        itemCompletedView.showCreator(itemDto);
    }

    public void markItemAsComplete(ItemCompletedDto itemCompletedDto) {
        mainWindowPresenter.markDone(itemCompletedDto.getId(), itemCompletedDto.getSummary());
    }
}
