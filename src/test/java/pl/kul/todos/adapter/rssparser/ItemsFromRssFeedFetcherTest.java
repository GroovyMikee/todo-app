package pl.kul.todos.adapter.rssparser;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

public class ItemsFromRssFeedFetcherTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void fetch_should_return_mapped_items_from_given_rss_channel() throws IOException {
        // given
        ItemsFromRssFeedFetcher fetcher = new ItemsFromRssFeedFetcher();

        // and
        URL input = URI.create(wireMockRule.baseUrl()).resolve("/rss").toURL();

        // and
        stubFor(get("/rss").willReturn(aResponse()
                .withStatus(200)
                .withBody(getFileContent())
        ));

        // when
        List<FeedItemDto> result = fetcher.fetch(input);

        // then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result)
                .extracting(itemDto -> itemDto.getTitle())
                .containsExactly("Box Old Objects to Be Autoclosable", "Random Sampling From a File");
        Assertions.assertThat(result)
                .extracting(itemDto -> itemDto.getURL())
                .containsExactly(
                        new URL("https://dzone.com/articles/box-old-objects-to-be-autoclosable"),
                        new URL("https://dzone.com/articles/random-sampling-from-a-file")
                );
    }

    private String getFileContent() throws IOException {
        Path path = Paths.get(getClass().getResource("/rss-feed-example.xml").getFile());

        return Files.lines(path)
                .collect(Collectors.joining());
    }
}