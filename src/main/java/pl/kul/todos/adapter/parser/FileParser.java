package pl.kul.todos.adapter.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileParser {
    private final LineParser lineParser;

    FileParser(LineParser lineParser) {
        this.lineParser = lineParser;
    }

    public List<Item> parse(Path path) {
        try {
            return Files.lines(path)
                    .map(line -> lineParser.lineToItem(line))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
