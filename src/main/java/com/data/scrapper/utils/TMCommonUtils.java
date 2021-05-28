package com.data.scrapper.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class TMCommonUtils {

    public static int getWordCount(String text) {
         return  text.trim().split("\\s+").length;
    }

    public static int getWordCount(File file) throws IOException {
        int wordCount=0;
        var lines = FileUtils.readLines(file, Charset.defaultCharset());
        for(var line: lines){
            wordCount = wordCount + getWordCount(line);
        }
        return wordCount;
    }

}
