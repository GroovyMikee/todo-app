package pl.kul.todos.adapter.gui.mainwindow;

import java.util.List;

public interface MainWindowView {
    void setPresenter(MainWindowPresenter presenter);

    void displayTodoItems(List<ItemDto> todos);
}
