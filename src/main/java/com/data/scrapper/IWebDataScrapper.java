package com.data.scrapper;

import java.io.File;
import java.io.IOException;

public interface IWebDataScrapper {
    public long scrapeDataFromURL() throws IOException;
}
