package com.deckerchan.ml.io;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.Path;
import java.util.ArrayList;

public class EmailFormatDocument extends Document {
    public EmailFormatDocument(Path filePath) {
        super(filePath);
    }

    private ArrayList<String> emailFields;

    public ArrayList<String> getEmailFields() {
        if (emailFields == null) {
            emailFields = new ArrayList<>();
            String content = super.getContent();
        }
        throw new NotImplementedException();
    }


    @Override
    public String getContent()  {
        return super.getContent();
    }
}
