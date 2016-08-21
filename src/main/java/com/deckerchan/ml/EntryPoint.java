package com.deckerchan.ml;

import com.deckerchan.ml.classifier.entities.*;
import com.deckerchan.ml.classifier.k.KMedoidsClassifier;
import com.deckerchan.ml.io.EmailFormatDocument;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class EntryPoint {
    public static void main(String[] args) throws Exception {


        ArrayList<EmailFormatDocument> documents = Files
                .walk(Paths.get("../data/two_newsgroups"))
                .filter(Files::isRegularFile)
                .map(EmailFormatDocument::new).collect(Collectors.toCollection(ArrayList<EmailFormatDocument>::new));

        documents.parallelStream().forEach(EmailFormatDocument::calculateWordFrequencyTable);

        //Get total word frequency table

        WordFrequencyTable totalTable = documents.stream()
                .map(EmailFormatDocument::getWordFrequencyTable)
                .reduce(new WordFrequencyTable(), WordFrequencyTable::mergeTable);


        List<Dimension> dimensionList = totalTable
                .entrySet().parallelStream()
                .map(stringLongEntry -> new Dimension(stringLongEntry.getKey(), 1))
                .collect(Collectors.toList());


        List<RealItemHDPoint> documentPoints = documents.parallelStream()
                .map(emailFormatDocument -> {
                    DimensionValueMap initialDimensionMap = new DimensionValueMap(dimensionList);

                    emailFormatDocument.getWordFrequencyTable()
                            .entrySet().stream()
                            .forEach(wordFrequencyEntry ->
                                    initialDimensionMap.entrySet().stream()
                                            .filter(dimensionValueEntry ->
                                                    dimensionValueEntry.getKey().getDimensionName().equals(wordFrequencyEntry.getKey()))
                                            .forEach(dimensionDoubleEntry -> dimensionDoubleEntry.setValue(wordFrequencyEntry.getValue())));
                    out.printf("Finish build hyper point for document %s%n", emailFormatDocument.getFileName());
                    return new RealItemHDPoint<EmailFormatDocument>(emailFormatDocument, initialDimensionMap);
                })
                .collect(Collectors.toList());

        KMedoidsClassifier classifier =new KMedoidsClassifier(2,documentPoints);

        classifier.calculate(20);

        out.println(classifier.report());


        System.exit(0);


    }
}
