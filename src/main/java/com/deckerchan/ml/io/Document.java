package com.deckerchan.ml.io;

import com.deckerchan.ml.classifier.entities.WordFrequencyBasedValueTable;
import com.deckerchan.ml.classifier.utils.LongStopWordList;
import com.deckerchan.ml.classifier.utils.TokenlizerUtils;
import org.apache.commons.lang3.StringUtils;
import org.tartarus.snowball.ext.PorterStemmer;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Document {

    private String documentText;
    private String title;
    private String content;
    private WordFrequencyBasedValueTable wordFrequencyBasedValueTable;
    private Path filePath;

    public Document(Path filePath) {
        this.wordFrequencyBasedValueTable = new WordFrequencyBasedValueTable();
        this.filePath = filePath;

    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }

    public String getDocumentText() {
        try {
            if (this.documentText == null) {
                byte[] encoded = Files.readAllBytes(this.getFilePath());
                this.documentText = new String(encoded, Charset.defaultCharset());
            }
            return this.documentText;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public Path getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return this.filePath.getFileName().toString();
    }

    public WordFrequencyBasedValueTable getWordFrequencyBasedValueTable() {
        if (this.wordFrequencyBasedValueTable == null) {
            this.calculateWordFrequencyTable();
        }
        return this.wordFrequencyBasedValueTable;
    }

    public abstract void resolveDocument();


    public void calculateWordFrequencyTable() {
        try {
            this.resolveDocument();

            PorterStemmer stemmer = new PorterStemmer();
            this.wordFrequencyBasedValueTable = new WordFrequencyBasedValueTable();

            List<String> words = TokenlizerUtils.removeOverLengthWords(TokenlizerUtils.removeStopWords(TokenlizerUtils.removeNumber(TokenlizerUtils.tokenlize(this.content))));

            for (String word : words) {


                this.wordFrequencyBasedValueTable.occure(word, Math.pow(word.length(), 2) / (double) words.size());
            }

            for (String word : TokenlizerUtils.removeStopWords(TokenlizerUtils.tokenlize(this.getTitle()))) {

                word = word.toLowerCase();

                word = word.replaceAll("[^A-Za-z]", "");
                if (StringUtils.isBlank(word) || StringUtils.isEmpty(word)) {
                    continue;
                }

                if (LongStopWordList.getStopWordList().contains(word)) {
                    continue;
                }

                this.wordFrequencyBasedValueTable.occure(word, 3D);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
