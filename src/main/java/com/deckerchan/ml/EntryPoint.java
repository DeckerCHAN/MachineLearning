package com.deckerchan.ml;

import com.deckerchan.ml.classifier.entities.Coordinate;
import com.deckerchan.ml.classifier.entities.Dimension;
import com.deckerchan.ml.classifier.entities.RealItemHDPoint;
import com.deckerchan.ml.classifier.entities.WordFrequencyBasedValueTable;
import com.deckerchan.ml.classifier.k.KClassifierBase;
import com.deckerchan.ml.classifier.k.KMeansClassifier;
import com.deckerchan.ml.classifier.k.KMedoidsClassifier;
import com.deckerchan.ml.io.Document;
import com.deckerchan.ml.io.EmailFormatDocument;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class EntryPoint {
    public static void main(String[] args) throws Exception {


        ArrayList<Document> documents = Files
                .walk(Paths.get(args[0]))
                .filter(Files::isRegularFile)
                .map(EmailFormatDocument::new)
                .collect(Collectors.toCollection(ArrayList<Document>::new));

        documents.parallelStream().forEach(Document::calculateWordFrequencyTable);

        //Get total word frequency table

        WordFrequencyBasedValueTable totalTable = new WordFrequencyBasedValueTable();
        totalTable.mergeTable(documents.stream()
                .map(Document::getWordFrequencyBasedValueTable)
                .reduce(new WordFrequencyBasedValueTable(), WordFrequencyBasedValueTable::mergeTable).getSortedTableOrderByValue(400));


        //idf
        for (String word : totalTable.getWords()) {
            Long df = 0L;

            for (Document doc : documents) {
                if(doc.getWordFrequencyBasedValueTable().getWords().contains(word)){
                    df++;
                }
            }

            int totoalDocumentNumner = documents.size();
            Double idf = Math.log10(totoalDocumentNumner / df);
            documents.stream().filter(document -> document.getWordFrequencyBasedValueTable().getWords().contains(word))
                    .forEach(newDoc -> newDoc.getWordFrequencyBasedValueTable().put(word, newDoc.getWordFrequencyBasedValueTable().get(word) * idf));
        }

        List<Dimension> dimensionList = totalTable
                .entrySet().parallelStream()
                .map(stringLongEntry -> new Dimension(stringLongEntry.getKey()))
                .collect(Collectors.toList());


        List<RealItemHDPoint> documentPoints = documents.parallelStream()
                .map(document -> {
                    Coordinate initialDimensionMap = new Coordinate(dimensionList);


                    for (String word : document.getWordFrequencyBasedValueTable().keySet()) {
                        initialDimensionMap.set(word, document.getWordFrequencyBasedValueTable().get(word));
                    }

                    out.printf("Finish build hyper point for document %s%n", document.getFileName());
                    return new RealItemHDPoint<>(document, initialDimensionMap);
                })
                .collect(Collectors.toList());

        KClassifierBase classifier = new KMedoidsClassifier(Integer.valueOf(args[1]), documentPoints);

        classifier.calculate(50);

        classifier.report();


        System.exit(0);


    }
}
