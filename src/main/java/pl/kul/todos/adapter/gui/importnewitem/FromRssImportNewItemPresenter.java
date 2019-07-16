package pl.kul.todos.adapter.gui.importnewitem;

import pl.kul.todos.adapter.gui.mainwindow.ItemDto;
import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;
import pl.kul.todos.adapter.rssparser.FeedItemDto;
import pl.kul.todos.adapter.rssparser.ItemsFromRssFeedFetcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class FromRssImportNewItemPresenter implements ImportNewItemPresenter {
    private final MainWindowPresenter mainWindowPresenter;
    private final FxFromRssImportNewItemView view;

    public FromRssImportNewItemPresenter(MainWindowPresenter mainWindowPresenter, FxFromRssImportNewItemView view) {
        this.mainWindowPresenter = mainWindowPresenter;
        this.view = view;
    }

    @Override
    public void showImporter() {
        view.showImporter();
    }

    public void importNewRssSource(NewRssSourceDto newRssSourceDto) {
        try {
            URL url = new URL(newRssSourceDto.getRssUrl());
            List<FeedItemDto> importedItems = new ItemsFromRssFeedFetcher().fetch(url);

            List<ItemDto> items = importedItems.stream()
                    .map(feedItemDto -> new ItemDto(feedItemDto.getTitle(), feedItemDto.getURL().toString()))
                    .collect(Collectors.toList());

            mainWindowPresenter.addNewItem(items);
        } catch (MalformedURLException e) {
            view.showError("Podano nieprawidłowy adres kanału RSS");
        }
    }
}
