package com.deckerchan.ml.io;

import com.deckerchan.ml.classifier.entities.WordFrequencyTable;
import org.apache.commons.lang3.StringUtils;
import org.tartarus.snowball.ext.PorterStemmer;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Document {

    private String documentText;
    private String title;
    private String content;
    private WordFrequencyTable wordFrequencyTable;
    private Path filePath;

    public Document(Path filePath) {
        this.wordFrequencyTable = new WordFrequencyTable();
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

    public WordFrequencyTable getWordFrequencyTable() {
        if (this.wordFrequencyTable == null) {
            this.calculateWordFrequencyTable();
        }
        return this.wordFrequencyTable;
    }

    public abstract void resolveDocument();


    public void calculateWordFrequencyTable() {
        try {
            this.resolveDocument();

            PorterStemmer stemmer = new PorterStemmer();
            this.wordFrequencyTable = new WordFrequencyTable();

            String[] words = this.getContent().split("[\\p{Punct}\\s]+");

            for (String word : words) {

                word = word.toLowerCase();

                word = word.replaceAll("[^A-Za-z]", "");
                if (StringUtils.isBlank(word) || StringUtils.isEmpty(word)) {
                    continue;
                }

                if (org.apache.lucene.analysis.en.EnglishAnalyzer.getDefaultStopSet().contains(word)) {
                    continue;
                }

                if(word.length()<3){
                    continue;
                }

                stemmer.setCurrent(word);
                stemmer.stem();
                String stemedWord = stemmer.getCurrent();

                this.wordFrequencyTable.occure(word);
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
