package com.deckerchan.ml.classifier.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.AttributeFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TokenlizerUtils {
    public static List<String> tokenlize(String content) throws IOException {
        CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
        StandardTokenizer tokenStream = new StandardTokenizer(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
        tokenStream.setReader(new StringReader(content));
        tokenStream.reset();

        List<String> words = new ArrayList<>();

        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            words.add(attr.toString().toLowerCase());

        }

        return words;
    }

    public static List<String> removeNumber(List<String> words) {
        ArrayList<String> newList = new ArrayList<>();
        for (String word :
                words) {
            word = word.replaceAll("[^A-Za-z]", "");
            if (StringUtils.isNotEmpty(word) && StringUtils.isNoneBlank(word)) {
                newList.add(word);
            }


        }
        return newList;
    }

    public static List<String> removeStopWords(List<String> words)
    {
        return words.stream().filter(w-> !LongStopWordList.getStopWordList().contains(w)).collect(Collectors.toList());
    }

    public static List<String> removeOverLengthWords(List<String> words)
    {

        return words.stream().filter(w-> w.length()>1 && w.length()<15).collect(Collectors.toList());
    }
}
