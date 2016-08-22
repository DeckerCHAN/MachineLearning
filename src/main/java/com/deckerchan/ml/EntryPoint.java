package com.deckerchan.ml;

import com.deckerchan.ml.classifier.entities.*;
import com.deckerchan.ml.classifier.k.KClassifierBase;
import com.deckerchan.ml.classifier.k.KMeansClassifier;
import com.deckerchan.ml.classifier.k.KMedoidsClassifier;
import com.deckerchan.ml.io.Document;
import com.deckerchan.ml.io.EmailFormatDocument;
import com.deckerchan.ml.io.PureTextDocument;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class EntryPoint {
    public static void main(String[] args) throws Exception {


        ArrayList<PureTextDocument> documents = Files
                .walk(Paths.get(args[0]))
                .filter(Files::isRegularFile)
                .map(PureTextDocument::new)
                .collect(Collectors.toCollection(ArrayList<PureTextDocument>::new));

        documents.parallelStream().forEach(Document::calculateWordFrequencyTable);

        //Get total word frequency table

        WordFrequencyBasedValueTable totalTable = new WordFrequencyBasedValueTable();
        totalTable.mergeTable(documents.stream()
                .map(Document::getWordFrequencyBasedValueTable)
                .reduce(new WordFrequencyBasedValueTable(), WordFrequencyBasedValueTable::mergeTable).getSortedTableOrderByValue(400));


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

        KClassifierBase classifier = new KMeansClassifier(Integer.valueOf(args[1]), documentPoints);

        classifier.calculate(50);

        classifier.report();




        System.exit(0);


    }
}
