package pl.kul.todos.adapter.parser;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileParserTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private FileParser fileParser;

    @Before
    public void setUp() {
        fileParser = new FileParserFactory().create();
    }

    @Test
    public void should_parse_entries_from_given_file() {
        // given
        Path inputFilePath = createFile(
                "\"2019-04-01\";\"First item\"",
                "\"2019-04-02\";\"Second item\""
        );

        // when
        List<Item> result = fileParser.parse(inputFilePath);

        // then
        Assertions.assertThat(result)
                .extracting(item -> item.getDescription())
                .containsExactly("First item", "Second item");
    }

    @Test
    public void should_throw_exception_when_any_line_does_not_match_pattern() {
        // given
        Path inputFilePath = createFile(
                "not matching line",
                "\"2019-04-02\";\"Second item\""
        );

        // when
        Throwable result = Assertions.catchThrowable(() -> fileParser.parse(inputFilePath));

        // then
        Assertions.assertThat(result).isInstanceOf(IllegalStateException.class)
                .hasMessage("\"not matching line\" does not match parsing pattern");
    }

    private Path createFile(String... lines) {
        try {
            Path inputFilePath = temporaryFolder.newFile().toPath();
            Files.write(inputFilePath, Arrays.asList(lines), StandardOpenOption.TRUNCATE_EXISTING);

            return inputFilePath;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}