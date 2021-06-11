package com.data.scrapper.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static long getWordCount(List<File> files) throws IOException {
        long wordCount = 0;
        for (var file : files) {
            wordCount = wordCount + getWordCount(file);
        }
        return  wordCount;
    }

    public static void main(String[] args) throws IOException {
        //long count = getWordCount(new File("/Users/sivanandareddy/Documents/data_science/output/content.txt"));
        //System.out.println("Word count in File : "+ count);
        var files = FileUtils.listFiles(new File("/Users/sivanandareddy/Documents/data_science/output/A_Z_individualFiles/"), null, true);
        System.out.println("Word count in File : "+ getWordCount(new ArrayList<>(files)));
    }

}
