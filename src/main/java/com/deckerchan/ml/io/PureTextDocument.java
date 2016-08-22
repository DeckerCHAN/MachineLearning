package com.deckerchan.ml.io;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PureTextDocument extends Document {
    public PureTextDocument(Path filePath) {
        super(filePath);
    }

    @Override
    public void resolveDocument() {
        List<String> lines = new LinkedList<>(Arrays.asList(this.getDocumentText().split("\\r?\\n", -1)));

        this.setTitle(lines.get(0));

        lines.remove(0);

        this.setContent(lines.stream().reduce("", (s, s2) -> {
            return s + "\r\n" + s2;
        }));
    }
}
