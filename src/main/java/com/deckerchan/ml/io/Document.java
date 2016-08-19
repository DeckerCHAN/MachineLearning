package com.deckerchan.ml.io;

import org.tartarus.snowball.ext.PorterStemmer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Map;

public abstract class Document {

    private String content;

    public String getContent() {
        try{
            if (this.content == null) {
                StringBuilder stringBuilder = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(this.getFilePath().toFile()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                }
                this.content = stringBuilder.toString();
            }
            return this.content;
        }catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }

    }


    private Hashtable<String, Long> wordFrequencyTable;

    public Document(Path filePath) {
        this.wordFrequencyTable = new Hashtable<>();
        this.filePath = filePath;

    }

    public Path getFilePath() {
        return filePath;
    }

    private Path filePath;


    public String getFileName() {
        return this.filePath.getFileName().toString();
    }

    public Map<String, Long> getWordFrequencyTable() throws IOException {
        if (this.wordFrequencyTable == null) {
            this.calculateWordFrequencyTable();
        }
        return this.wordFrequencyTable;
    }

    public void calculateWordFrequencyTable() {
        try {
            PorterStemmer stemmer = new PorterStemmer();
            this.wordFrequencyTable = new Hashtable<>();

            String[] words = this.getContent().split("[\\p{Punct}\\s]+");

            for (String word : words) {
                stemmer.setCurrent(word);
                stemmer.stem();
                String stemWord = stemmer.getCurrent();

                if (this.wordFrequencyTable.contains(stemWord)) {
                    this.wordFrequencyTable.put(stemWord, this.wordFrequencyTable.get(stemWord) + 1);
                } else {
                    this.wordFrequencyTable.put(stemWord, 1L);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


    }


}
