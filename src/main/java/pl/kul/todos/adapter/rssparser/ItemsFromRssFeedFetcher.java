package pl.kul.todos.adapter.rssparser;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ItemsFromRssFeedFetcher {
    public List<FeedItemDto> fetch(URL url) {
        try {
            SyndFeedInput syndFeedInput = new SyndFeedInput(true, Locale.forLanguageTag("pl"));
            SyndFeed syndFeed = syndFeedInput.build(new XmlReader(url));

            return syndFeed.getEntries()
                    .stream()
                    .map(syndEntry -> toFeedItemDto(syndEntry))
                    .collect(Collectors.toList());
        } catch (FeedException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private FeedItemDto toFeedItemDto(SyndEntry syndEntry) {
        try {
            return new FeedItemDto(syndEntry.getTitle(), new URL(syndEntry.getLink()));
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }
}
