package com.deckerchan.ml.io;

import com.deckerchan.ml.utils.RegexUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailFormatDocument extends Document {
    private HashMap<String, String> emailFields;

    public EmailFormatDocument(Path filePath) {
        super(filePath);
    }

    @Override
    public void resolveDocument() {

        StringBuilder emailContent = new StringBuilder();


        if (emailFields == null) {
            emailFields = new HashMap<>();
            String text = this.getDocumentText();

            Pattern isFiledMatchPattern = Pattern.compile(".*?:\\s.*");
            String selectKeyRegex = ".*?(?=:\\s.*)";
            String selectValueRegex = "(?<=.*?:\\s).*";

            boolean inHeadingArea = true;
            for (String line : text.split("\\r\\n|\\n|\\r")) {

                if (inHeadingArea) {
                    Matcher matcher = isFiledMatchPattern.matcher(line);
                    if (matcher.find()) {
                        String key = RegexUtils.getFirstFound(line, selectKeyRegex);
                        String value = RegexUtils.getFirstFound(line, selectValueRegex);

                        this.emailFields.put(key, value);
                    } else {
                        inHeadingArea = false;
                    }
                } else {
                    emailContent.append(line).append(System.getProperty("line.separator"));
                }
            }

            //Try to set subject as title.
            if (StringUtils.isNotEmpty(this.emailFields.get("Subject"))) {
                this.setTitle(this.emailFields.get("Subject"));
            } else {
                this.setTitle(this.getFilePath().getFileName().toString());
            }
            //Set content
            this.setContent(emailContent.toString());

        }
    }
}
