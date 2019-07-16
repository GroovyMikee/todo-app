package pl.kul.todos.adapter.gui.importnewitem;

public class NewRssSourceDto {
    private final String rssUrl;

    public NewRssSourceDto(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public String getRssUrl() {
        return rssUrl;
    }
}
