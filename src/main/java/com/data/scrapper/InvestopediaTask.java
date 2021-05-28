package com.data.scrapper;

import com.data.scrapper.utils.TMCommonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class InvestopediaTask implements IWebDataScrapper{

    private static Set<String> uniqueResults;
    private static int totalUrls;
    private static AtomicInteger wordCount = new AtomicInteger(0);
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println("Start time         : "+start);
        uniqueResults = new HashSet<String>();
        InvestopediaTask investopedia = new InvestopediaTask();
        var file  =investopedia.scrapeDataFromURL();
        //var wordCount = TMCommonUtils.getWordCount(file);
        long end = System.currentTimeMillis();
        System.out.println("End time           : "+ end);
        System.out.println("Unique URLs        : "+ uniqueResults.size());
        System.out.println("Word count in File : "+ wordCount);
        System.out.println("Time Taken         : "+(end- start)/1000 +"sec");
    }

    @Override
    public long  scrapeDataFromURL() throws IOException {
        var outputDir= "/Users/sivanandareddy/Documents/data_science/output/".concat(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)).concat("/");
        var files = FileUtils.listFiles(new File("/Users/sivanandareddy/Documents/data_science/input/"), null, false);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try{
            for (var inputFile : files) {
                var searchTextList = FileUtils.readLines(inputFile, Charset.defaultCharset());
                System.out.println("File : "+ inputFile.getName() + " #keywords : "+ searchTextList.size() );
                for(var searchText: searchTextList){
                   // System.out.println("SearchText : "+searchText+".... Total Unique URLs : "+ uniqueResults.size()+".....Total  URLs : "+ totalUrls);
                    var searchUrl = "https://www.investopedia.com/search?q=" + URLEncoder.encode(searchText, Charset.defaultCharset());
                    Document doc = Jsoup.connect(searchUrl).get();
                    if(doc.getElementById("search-pagination_1-0") != null){
                        var searchPaginationList = doc.getElementById("search-pagination_1-0").getElementsByTag("a");
                        if(!searchPaginationList.isEmpty()){
                            for (var paginationLink : searchPaginationList) {
                                var paginationUrl = paginationLink.getElementsByTag("a").attr("href");
                                if (paginationUrl.startsWith("http")) {
                                    //scrapeDataFromUrl(file, paginationUrl);
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            //System.out.println("current Thread: "+ Thread.currentThread().getName() +" page: "+paginationUrl);
                                            try {
                                                scrapeDataFromUrl(outputDir, searchUrl);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        scrapeDataFromUrl(outputDir, searchUrl);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return  0;
    }

    private void scrapeDataFromUrl(String  outPutDir, String paginationUrl) throws IOException {

        try{
            var searchResults = Jsoup.connect(paginationUrl).get().getElementById("search-results__results_1-0").getElementsByTag("a");
            var searchResultsList = new ArrayList<String>();
            for (var searchResult : searchResults) {
                //System.out.println(pageLink.text());
                searchResultsList.add(searchResult.getElementsByClass("search-results__url").text().trim());
                //System.out.println(searchResult.getElementsByClass("search-results__url").text());
            }

            for (var searchPageLink : searchResultsList) {
                totalUrls++;
                try {
                    if (!StringUtil.isBlank(searchPageLink) && uniqueResults.add(searchPageLink)) {
                        //System.out.println("******************" + searchPageLink + "******************");
                        var urlParts = searchPageLink.split("/");
                        var file = new File(outPutDir+urlParts[urlParts.length-1]);
                        Document searchPageDoc = Jsoup.connect(searchPageLink)
                                .timeout(10000)
                                .get();
                        var articleText="";
                        if(searchPageDoc.getElementById("article_1-0")!=null){
                            articleText = searchPageDoc.getElementById("article_1-0").text();
                        }else {
                            articleText = searchPageDoc.getElementsByClass("content").text();
                        }
                        FileUtils.writeStringToFile(file, articleText, Charset.defaultCharset(), true);
                        var wordCountInFile = TMCommonUtils.getWordCount(file);
                        wordCount.addAndGet(wordCountInFile);
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
