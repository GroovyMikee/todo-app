package pl.kul.todos.adapter.rssparser;

import java.net.URL;

public class FeedItemDto {
    private final String title;
    private final URL url;

    public FeedItemDto(String title, URL url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public URL getURL() {
        return url;
    }
}
