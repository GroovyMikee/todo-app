package pl.kul.todos.adapter.gui.itemdetails;

import javafx.application.HostServices;
import pl.kul.todos.adapter.gui.mainwindow.MainWindowPresenter;

public class ItemDetailsPresenterFactory {
    private final DescriptionConverter descriptionConverter;

    public ItemDetailsPresenterFactory(HostServices hostServices) {
        descriptionConverter = new DescriptionConverter(hostServices);
    }

    public ItemDetailsPresenter create(MainWindowPresenter mainWindowPresenter) {
        ItemDetailsView itemDetailsView = new FxItemDetailsView(descriptionConverter);
        ItemDetailsPresenter presenter = new ItemDetailsPresenter(itemDetailsView);
        itemDetailsView.setPresenter(presenter);

        return presenter;
    }
}
