package pl.kul.todos.adapter.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LineParser {
    private final Pattern linePattern;

    public LineParser() {
        linePattern = Pattern.compile("\"\\d{4}-\\d{2}-\\d{2}\";\"(?<description>.*?)\"");
    }

    public Item lineToItem(String line) {
        Matcher matcher = linePattern.matcher(line);

        if (matcher.matches()) {
            return new Item(matcher.group("description"));
        } else {
            throw new IllegalStateException(String.format("\"%s\" does not match parsing pattern", line));
        }
    }
}
