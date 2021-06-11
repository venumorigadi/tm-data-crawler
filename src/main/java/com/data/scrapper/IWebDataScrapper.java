package com.data.scrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IWebDataScrapper {
    public List<File> scrapeDataFromURL() throws IOException;
}
