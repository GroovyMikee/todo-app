package pl.kul.todos.adapter.gui.importnewitem;

interface FromFileImportNewItemView {
    void setPresenter(FromFileImportNewItemPresenter presenter);

    void showImporter();

    void showImportError(String message);
}
