package com.deckerchan.ml;

import com.deckerchan.ml.classifier.entities.*;
import com.deckerchan.ml.classifier.k.KClassifierBase;
import com.deckerchan.ml.classifier.k.KMeansClassifier;
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
                .walk(Paths.get(args[0]))
                .filter(Files::isRegularFile)
                .map(EmailFormatDocument::new)
                .collect(Collectors.toCollection(ArrayList<EmailFormatDocument>::new));

        documents.parallelStream().forEach(EmailFormatDocument::calculateWordFrequencyTable);

        //Get total word frequency table

        WordFrequencyBasedValueTable totalTable = new WordFrequencyBasedValueTable();
        totalTable.mergeTable(documents.stream()
                .map(EmailFormatDocument::getWordFrequencyBasedValueTable)
                .reduce(new WordFrequencyBasedValueTable(), WordFrequencyBasedValueTable::mergeTable).getSortedTableOrderByValue(200));


        List<Dimension> dimensionList = totalTable
                .entrySet().parallelStream()
                .map(stringLongEntry -> new Dimension(stringLongEntry.getKey()))
                .collect(Collectors.toList());


        List<RealItemHDPoint> documentPoints = documents.parallelStream()
                .map(emailFormatDocument -> {
                    Coordinate initialDimensionMap = new Coordinate(dimensionList);


                    for (String word : emailFormatDocument.getWordFrequencyBasedValueTable().keySet()) {
                        initialDimensionMap.set(word, emailFormatDocument.getWordFrequencyBasedValueTable().get(word));
                    }

                    out.printf("Finish build hyper point for document %s%n", emailFormatDocument.getFileName());
                    return new RealItemHDPoint<>(emailFormatDocument, initialDimensionMap);
                })
                .collect(Collectors.toList());

        KClassifierBase classifier = new KMedoidsClassifier(11, documentPoints);

        classifier.calculate(50);

        out.println(classifier.report());




        System.exit(0);


    }
}
