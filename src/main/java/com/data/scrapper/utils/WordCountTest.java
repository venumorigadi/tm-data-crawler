package com.data.scrapper.utils;

import java.io.File;
import java.io.IOException;

public class WordCountTest {
    public static void main(String[] args) throws IOException {
        var file= new File("/Users/sivanandareddy/Documents/tm-data/content_A-F.txt");
        long count = TMCommonUtils.getWordCount(file);
        System.out.println("Word count in File "+count);
    }
}
