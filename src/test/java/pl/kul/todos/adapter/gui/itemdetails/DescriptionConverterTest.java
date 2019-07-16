package pl.kul.todos.adapter.gui.itemdetails;

import javafx.application.HostServices;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DescriptionConverterTest {
    private HostServices hostServices;
    private DescriptionConverter descriptionConverter;

    @Before
    public void setUp() {
        hostServices = Mockito.mock(HostServices.class);

        descriptionConverter = new DescriptionConverter(hostServices);
    }

    @Test
    public void should_return_label_when_no_links() {
        // given
        String input = "This is text";

        // when
        CompositeTextToComponent result = descriptionConverter.toTextFlow(input);

        // then
        Assertions.assertThat(result.getTextFlow())
                .hasSize(1)
                .extracting(textToComponent -> textToComponent.getRawText())
                .containsExactly("This is text");
    }

    @Test
    public void should_return_hyperlink_when_only_link_present() {
        // given
        String input = "http://wp.pl/index?param=test";

        // when
        CompositeTextToComponent result = descriptionConverter.toTextFlow(input);

        // then
        Assertions.assertThat(result.getTextFlow()).hasSize(1)
                .hasOnlyElementsOfType(HyperLinkTextToComponent.class);

        Assertions.assertThat(result.getTextFlow()).hasSize(1)
                .extracting(textToComponent -> textToComponent.getRawText())
                .containsExactly("http://wp.pl/index?param=test");
    }

    @Test
    public void should_return_label_and_hyperlink_in_order_of_occurrence() {
        // given
        String input = "Wejdź proszę w tego linka: http://wp.pl/index?param=test";

        // when
        CompositeTextToComponent result = descriptionConverter.toTextFlow(input);

        // then
        Assertions.assertThat(result.getTextFlow()).hasSize(2);

        Assertions.assertThat(result.getTextFlow().get(0)).isInstanceOf(RawText.class)
                .extracting(textToComponent -> textToComponent.getRawText())
                .isEqualTo("Wejdź proszę w tego linka: ");

        Assertions.assertThat(result.getTextFlow().get(1)).isInstanceOf(HyperLinkTextToComponent.class)
                .extracting(textToComponent -> textToComponent.getRawText())
                .isEqualTo("http://wp.pl/index?param=test");
    }

    @Test
    public void should_return_labels_and_hyperlinks_in_order_of_occurrence() {
        // given
        String input = "Wejdź proszę w tego linka: http://wp.pl/index?param=test . Co o tym sądzisz? Albo o tym: https://wp.pl";

        // when
        CompositeTextToComponent result = descriptionConverter.toTextFlow(input);

        // then
        Assertions.assertThat(result.getTextFlow()).hasSize(4);

        Assertions.assertThat(result.getTextFlow().get(0)).isInstanceOf(RawText.class)
                .extracting(textToComponent -> textToComponent.getRawText())
                .isEqualTo("Wejdź proszę w tego linka: ");

        Assertions.assertThat(result.getTextFlow().get(1)).isInstanceOf(HyperLinkTextToComponent.class)
                .extracting(textToComponent -> textToComponent.getRawText())
                .isEqualTo("http://wp.pl/index?param=test");

        Assertions.assertThat(result.getTextFlow().get(2)).isInstanceOf(RawText.class)
                .extracting(textToComponent -> textToComponent.getRawText())
                .isEqualTo(" . Co o tym sądzisz? Albo o tym: ");

        Assertions.assertThat(result.getTextFlow().get(3)).isInstanceOf(HyperLinkTextToComponent.class)
                .extracting(textToComponent -> textToComponent.getRawText())
                .isEqualTo("https://wp.pl");
    }
}