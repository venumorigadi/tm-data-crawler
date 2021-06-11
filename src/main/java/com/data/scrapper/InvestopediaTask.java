package com.data.scrapper;

import com.data.scrapper.utils.TMCommonUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class InvestopediaTask implements IWebDataScrapper{

    private static final String OUTPUT_DIR="/Users/sivanandareddy/Documents/data_science/output/09062021-2/";

    private static Set<String> uniqueResults;
    private static AtomicInteger totalUrls = new AtomicInteger(0);
    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("Start time         : "+start);
        uniqueResults = new HashSet<String>();
        File uniqueURLsFile = new File(OUTPUT_DIR+"uniqueURLs.txt");
        if(!uniqueURLsFile.exists()) uniqueURLsFile.createNewFile();
        uniqueResults.addAll(FileUtils.readLines(uniqueURLsFile, Charset.defaultCharset()));
        InvestopediaTask investopedia = new InvestopediaTask();
        var file  =investopedia.scrapeDataFromURL();
        Thread.sleep(180000);
        var wordCount = TMCommonUtils.getWordCount(file);
        long end = System.currentTimeMillis();
        System.out.println("End time           : "+ end);
        System.out.println("Unique URLs        : "+ uniqueResults.size());
        System.out.println("Word count in File : "+ wordCount );
        System.out.println("Time Taken         : "+(end- start)/1000 +"sec");
        uniqueResults.forEach(uniqueResult -> {
            try {
                FileUtils.writeStringToFile(new File(OUTPUT_DIR+"uniqueURLs.txt"), uniqueResult, Charset.defaultCharset(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<File> scrapeDataFromURL() throws IOException {
        var filesList = new ArrayList<File>();
       // File file = new File("/Users/sivanandareddy/Documents/data_science/output/content.txt");
        var files = FileUtils.listFiles(new File("/Users/sivanandareddy/Documents/data_science/input/"), null, false);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        files.forEach(System.out::println);
        try{
            for (var inputFile : files) {
                //File file = new File(OUTPUT_DIR+inputFile.getName()+"_content.txt");
                //filesList.add(file);
                var searchTextList = FileUtils.readLines(inputFile, Charset.defaultCharset());

                System.out.println("File : "+ inputFile.getName() + " #keywords : "+ searchTextList.size() );
                for(var searchText: searchTextList) {
                    try {
                        System.out.println("SearchText : " + searchText + ".... Total Unique URLs : " + uniqueResults.size() + ".....Total  URLs : " + totalUrls);
                        var searchUrl = "https://www.investopedia.com/search?q=" + URLEncoder.encode(searchText, Charset.defaultCharset());
                        Document doc = Jsoup.connect(searchUrl).get();
                        if (doc.getElementById("search-pagination_1-0") != null) {
                            var searchPaginationList = doc.getElementById("search-pagination_1-0").getElementsByTag("a");
                            if (!searchPaginationList.isEmpty()) {
                                searchPaginationList.parallelStream().forEach(paginationLink -> {
                                    var paginationUrl = paginationLink.getElementsByTag("a").attr("href");
                                    if (paginationUrl.startsWith("http")) {
                                    /*try {
                                        scrapeDataFromUrl(file, paginationUrl);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }*/
                                        executorService.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                //System.out.println("current Thread: "+ Thread.currentThread().getName() +" page: "+paginationUrl);
                                                try {
                                                    scrapeDataFromUrl(null, paginationUrl);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        } else {
                            scrapeDataFromUrl(null, searchUrl);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                Thread.sleep(120000);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return  filesList;
    }

    private void scrapeDataFromUrl(File file, final String paginationUrl) throws IOException {
        try{
            var searchResults = Jsoup.connect(paginationUrl).get().getElementById("search-results__results_1-0").getElementsByTag("a");
            var searchResultsList = new ArrayList<String>();
            for (var searchResult : searchResults) {
                //System.out.println(pageLink.text());
                searchResultsList.add(searchResult.getElementsByClass("search-results__url").text().trim());
                //System.out.println(searchResult.getElementsByClass("search-results__url").text());
            }

            for (var searchPageLink : searchResultsList) {
                totalUrls.incrementAndGet();
                try {
                    if (!StringUtil.isBlank(searchPageLink) && uniqueResults.add(searchPageLink)) {
                        //System.out.println("******************" + searchPageLink + "******************");
                        Document searchPageDoc = null;
                        try{
                            searchPageDoc = Jsoup.connect(searchPageLink)
                                    .timeout(15000)
                                    .get();
                        } catch (Exception e){
                            searchPageDoc = Jsoup.connect(searchPageLink)
                                    .timeout(15000)
                                    .get();
                        }
                        var articleText="";
                        if(searchPageDoc.getElementById("article_1-0")!=null){
                            articleText = searchPageDoc.getElementById("article_1-0").text();
                        }else {
                            articleText = searchPageDoc.getElementsByClass("content").text();
                        }
                        var urlParts = searchPageLink.split("/");
                        var individualFile = new File(OUTPUT_DIR+urlParts[urlParts.length-1]);

                        //FileUtils.writeStringToFile(individualFile, searchPageLink+"\n"+articleText, Charset.defaultCharset(), true);
                        FileUtils.writeStringToFile(individualFile, articleText, Charset.defaultCharset());
                        //FileUtils.writeStringToFile(file, articleText, Charset.defaultCharset(), true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
