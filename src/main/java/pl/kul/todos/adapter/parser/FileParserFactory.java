package pl.kul.todos.adapter.parser;

public class FileParserFactory {
    public FileParser create() {
        return new FileParser(new LineParser());
    }
}
