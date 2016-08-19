package com.deckerchan.ml;

import com.deckerchan.ml.classfier.entities.Dimension;
import com.deckerchan.ml.classfier.entities.DimensionValueMap;
import com.deckerchan.ml.classfier.entities.HyperDimensionPoint;
import com.deckerchan.ml.io.Document;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class EntryPoint {
    public static void main(String[] args) throws Exception {


       Stream<Document> textFiles =  Files
               .walk(Paths.get("../data/two_newsgroups"))
               .filter(Files::isRegularFile)
               .map(Document::new);
        textFiles.forEach(Document::calculateWordFrequencyTable);




        Dimension x = new Dimension("x");
        Dimension y = new Dimension("y");

        DimensionValueMap m1p = new DimensionValueMap();
        m1p.put(x,2.0);
        m1p.put(y,4.9);
        HyperDimensionPoint p1 = new HyperDimensionPoint(m1p);

        DimensionValueMap m2p = new DimensionValueMap();
        m1p.put(x,3.0);
        m1p.put(y,7.9);
        HyperDimensionPoint p2 = new HyperDimensionPoint(m2p);



    }
}
