package com.deckerchan.ml;

import com.deckerchan.ml.classifier.entities.*;
import com.deckerchan.ml.classifier.k.KClassifierBase;
import com.deckerchan.ml.classifier.k.KMeansClassifier;
import com.deckerchan.ml.classifier.k.KMedoidsClassifier;
import com.deckerchan.ml.io.EmailFormatDocument;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class EntryPoint {
    public static void main(String[] args) throws Exception {

//
//        ArrayList<EmailFormatDocument> documents = Files
//                .walk(Paths.get(args[0]))
//                .filter(Files::isRegularFile)
//                .map(EmailFormatDocument::new)
//                .collect(Collectors.toCollection(ArrayList<EmailFormatDocument>::new));
//
//        documents.parallelStream().forEach(EmailFormatDocument::calculateWordFrequencyTable);
//
//        //Get total word frequency table
//
//        WordFrequencyTable totalTable = new WordFrequencyTable();
//        totalTable.mergeTable(documents.stream()
//                .map(EmailFormatDocument::getWordFrequencyTable)
//                .reduce(new WordFrequencyTable(), WordFrequencyTable::mergeTable).getSortedTableOrderByValue(2000));
//
//
//        List<Dimension> dimensionList = totalTable
//                .entrySet().parallelStream()
//                .map(stringLongEntry -> new Dimension(stringLongEntry.getKey(), 1))
//                .collect(Collectors.toList());
//
//
//        List<RealItemHDPoint> documentPoints = documents.parallelStream()
//                .map(emailFormatDocument -> {
//                    DimensionValueMap initialDimensionMap = new DimensionValueMap(dimensionList);
//
//
//                    for (String word : emailFormatDocument.getWordFrequencyTable().keySet()) {
//                        initialDimensionMap.set(word, Double.valueOf(emailFormatDocument.getWordFrequencyTable().get(word)));
//                    }
//
//                    out.printf("Finish build hyper point for document %s%n", emailFormatDocument.getFileName());
//                    return new RealItemHDPoint<>(emailFormatDocument, initialDimensionMap);
//                })
//                .collect(Collectors.toList());
//
//        KClassifierBase classifier = new KMedoidsClassifier(2, documentPoints);
//
//        classifier.calculate(20);
//
//        out.println(classifier.report());


        //Test
        ArrayList<Dimension> list2D = new ArrayList<>();
        Dimension x = new Dimension("x");
        Dimension y = new Dimension("y");
        list2D.add(x);
        list2D.add(y);


        List<RealItemHDPoint> testPointList = new ArrayList<>();


        DimensionValueMap pm1 = new DimensionValueMap(list2D);
        pm1.set(x, 1.0);
        pm1.set(y, 1.0);
        RealItemHDPoint<String> p1 = new RealItemHDPoint<>("1=>1,1", pm1);
        testPointList.add(p1);

        DimensionValueMap pm2 = new DimensionValueMap(list2D);
        pm2.set(x, 1.5);
        pm2.set(y, 2.0);
        RealItemHDPoint<String> p2 = new RealItemHDPoint<>("2=>1.5,2", pm2);
        testPointList.add(p2);

        DimensionValueMap pm3 = new DimensionValueMap(list2D);
        pm3.set(x, 3.0);
        pm3.set(y, 4.0);
        RealItemHDPoint<String> p3 = new RealItemHDPoint<>("3=>3,4", pm3);
        testPointList.add(p3);

        DimensionValueMap pm4 = new DimensionValueMap(list2D);
        pm4.set(x, 5.0);
        pm4.set(y, 7.0);
        RealItemHDPoint<String> p4 = new RealItemHDPoint<>("4=>5,7", pm4);
        testPointList.add(p4);

        DimensionValueMap pm5 = new DimensionValueMap(list2D);
        pm5.set(x, 3.5);
        pm5.set(y, 5.0);
        RealItemHDPoint<String> p5 = new RealItemHDPoint<>("5=>3.5,5", pm5);
        testPointList.add(p5);

        DimensionValueMap pm6 = new DimensionValueMap(list2D);
        pm6.set(x, 4.5);
        pm6.set(y, 5.0);
        RealItemHDPoint<String> p6 = new RealItemHDPoint<>("6=>4.5,5", pm6);
        testPointList.add(p6);

        DimensionValueMap pm7 = new DimensionValueMap(list2D);
        pm7.set(x, 3.5);
        pm7.set(y, 4.5);
        RealItemHDPoint<String> p7 = new RealItemHDPoint<>("7=>3.5,4.5", pm7);
        testPointList.add(p7);


        KMeansClassifier testClassifier =new KMeansClassifier(2,testPointList);
        testClassifier.calculate(20);
        out.println(testClassifier.report());

        System.exit(0);


    }
}
