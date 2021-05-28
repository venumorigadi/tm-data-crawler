package com.data.scrapper;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Investopedia implements IWebDataScrapper {
    private static Set<String> uniqueResults;
    private static int totalUrls;
    public static void main(String[] args) throws IOException {
        uniqueResults = new HashSet<String>();
        Investopedia investopedia = new Investopedia();
        investopedia.scrapeDataFromURL();
       // System.out.println(URLEncoder.encode("Analysis of Variance (ANOVA)", Charset.defaultCharset()));
    }

    @Override
    public long scrapeDataFromURL() throws IOException {
        File file = new File("/Users/sivanandareddy/Documents/data_science/output/content.txt");
        var files = FileUtils.listFiles(new File("/Users/sivanandareddy/Documents/data_science/input/"), null, false);

        for (var inputFile : files) {
            var searchTextList = FileUtils.readLines(inputFile, Charset.defaultCharset());
            for(var searchText: searchTextList){
                System.out.println("SearchText : "+searchText+".... Total Unique URLs : "+ uniqueResults.size()+".....Total  URLs : "+ totalUrls);
                var searchUrl = "https://www.investopedia.com/search?q=" + URLEncoder.encode(searchText, Charset.defaultCharset());
                Document doc = Jsoup.connect(searchUrl).get();
                if(doc.getElementById("search-pagination_1-0") != null){
                    var searchPaginationList = doc.getElementById("search-pagination_1-0").getElementsByTag("a");
                    if(!searchPaginationList.isEmpty()){
                        for (var paginationLink : searchPaginationList) {
                            var paginationUrl = paginationLink.getElementsByTag("a").attr("href");
                            if (paginationUrl.startsWith("http")) {
                                scrapeDataFromUrl(file, paginationUrl);
                            }
                        }
                    }
                } else {
                    scrapeDataFromUrl(file, searchUrl);
                }
            }
        }
        return  0;
    }

    private void scrapeDataFromUrl(File file, String paginationUrl) throws IOException {
        try{
            var searchResults = Jsoup.connect(paginationUrl).get().getElementById("search-results__results_1-0").getElementsByTag("a");
            var searchResultsList = new ArrayList<String>();
            for (var searchResult : searchResults) {
                //System.out.println(pageLink.text());
                searchResultsList.add(searchResult.getElementsByClass("search-results__url").text().trim());

                //System.out.println(searchResult.getElementsByClass("search-results__url").text());
            }

            for (var searchPageLink : searchResultsList) {
                //article_1-0
                totalUrls++;
                try {
                    if (!StringUtil.isBlank(searchPageLink) && uniqueResults.add(searchPageLink)) {
                        //System.out.println("******************" + searchPageLink + "******************");
                        Document searchPageDoc = Jsoup.connect(searchPageLink)
                                .timeout(5000)
                                .get();
                        var articleText="";
                        if(searchPageDoc.getElementById("article_1-0")!=null){
                            articleText = searchPageDoc.getElementById("article_1-0").text();
                        }else {
                            articleText = searchPageDoc.getElementsByClass("content").text();
                        }
                        FileUtils.writeStringToFile(file, articleText, Charset.defaultCharset(), true);
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
