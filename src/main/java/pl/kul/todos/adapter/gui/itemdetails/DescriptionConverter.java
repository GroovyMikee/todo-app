package pl.kul.todos.adapter.gui.itemdetails;

import javafx.application.HostServices;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

interface TextToComponent {
    Node mapToComponent();

    String getRawText();
}

class DescriptionConverter {
    private final HostServices hostServices;

    DescriptionConverter(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public CompositeTextToComponent toTextFlow(String description) {
        Pattern compile = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");

        Matcher matcher = compile
                .matcher(description);

        List<TextToComponent> textFlow = new LinkedList<>();

        int lastMatchEndIndex = 0;

        while (matcher.find()) {
            int matchStartIndex = matcher.start();
            int matchEndIndex = matcher.end();

            String inBetweenFounds = description.substring(lastMatchEndIndex, matchStartIndex);
            String foundCandidate = description.substring(matchStartIndex, matchEndIndex);

            if (!inBetweenFounds.isBlank()) {
                textFlow.add(new RawText(inBetweenFounds));
            }

            textFlow.add(new HyperLinkTextToComponent(foundCandidate, hostServices));

            lastMatchEndIndex = matchEndIndex;
        }

        if (lastMatchEndIndex == 0) {
            textFlow.add(new RawText(description));
        }

        return new CompositeTextToComponent(textFlow);
    }
}

abstract class AbstractTextToComponent implements TextToComponent {
    private final String rawText;

    protected AbstractTextToComponent(String rawText) {
        this.rawText = rawText;
    }

    public String getRawText() {
        return rawText;
    }
}

class CompositeTextToComponent implements TextToComponent {
    private final List<TextToComponent> textFlow;

    public CompositeTextToComponent(List<TextToComponent> textFlow) {
        this.textFlow = textFlow;
    }

    public List<TextToComponent> getTextFlow() {
        return textFlow;
    }

    @Override
    public Node mapToComponent() {
        List<Node> components = textFlow.stream()
                .map(textToComponent -> textToComponent.mapToComponent())
                .collect(Collectors.toList());

        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().addAll(components);
        return textFlow;
    }

    @Override
    public String getRawText() {
        return textFlow.stream()
                .map(textToComponent -> textToComponent.getRawText())
                .collect(Collectors.joining(" "));
    }
}

class RawText extends AbstractTextToComponent {
    protected RawText(String rawText) {
        super(rawText);
    }

    @Override
    public Node mapToComponent() {
        return new Label(getRawText());
    }
}

class HyperLinkTextToComponent extends AbstractTextToComponent {
    private final HostServices hostServices;

    protected HyperLinkTextToComponent(String rawText, HostServices hostServices) {
        super(rawText);

        this.hostServices = hostServices;
    }

    @Override
    public Node mapToComponent() {
        Hyperlink hyperlink = new Hyperlink(getRawText());
        hyperlink.setOnAction(event -> {
            hostServices.showDocument(getRawText());
        });
        return hyperlink;
    }
}