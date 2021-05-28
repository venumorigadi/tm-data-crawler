package com.data.scrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChromeExample1 {
    private static final String URL = "https://learning.oreilly.com/accounts/login-check/";
    private static final String URL1 = "https://learning.oreilly.com/library/view/treasury-markets-and/9780470827604/";

    public static void main(String[] args) throws InterruptedException {

        //Setting system properties of ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/Users/sivanandareddy/Softwares/chromedriver");
        //Creating an object of ChromeDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        try {
            //Deleting all the cookies
            //driver.manage().deleteAllCookies();

            //Specifiying pageLoadTimeout and Implicit wait
            driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            //launching the specified URL
            driver.get(URL);

//Locating the elements using name locator for the text box
            driver.findElement(By.name("email")).sendKeys("v.morigadi@gmail.com");
            driver.findElement(By.name("password")).sendKeys("Oreilly@2021");

//name locator for google search button
            WebElement loginBtn = driver.findElement(By.className("orm-Button-root"));
            loginBtn.click();
            System.out.println("Login successful");
            driver.switchTo().window(driver.getWindowHandle());
            Thread.sleep(5000);
            WebElement searchField = driver.findElement(By.className("orm-Input-input"));
            searchField.sendKeys("Treasury and Market");
            searchField.sendKeys(Keys.ENTER);
            System.out.println("searching...");
            driver.switchTo().window(driver.getWindowHandle());
            List<WebElement> bookLinks = driver.findElements(By.tagName("article"));
            System.out.println("showing book links:" + bookLinks.size());
            for (var bookLink : bookLinks) {
                try{
                    bookLink.findElement(By.tagName("a")).click();
                    driver.switchTo().window(driver.getWindowHandle());
                    List<WebElement> indexLinks = driver.findElement(By.className("detail-toc")).findElements(By.tagName("a"));
                    //System.out.println(indexLinks.get(0).getText());
                    indexLinks.get(0).click();
                    driver.switchTo().window(driver.getWindowHandle());
                    boolean hasNextPage = true;
                    while (hasNextPage) {
                        try {
                            String chapterContent = driver.findElement(By.id("sbo-rt-content")).getText();
                            System.out.println(chapterContent);
                            System.out.println("*******************");
                            // driver.switchTo().window(driver.getWindowHandle());
                            driver.findElement(By.className("t-sbo-next")).findElement(By.tagName("a")).click();
                            Thread.sleep(200);
                            //driver.getWindowHandles().forEach(windowHandle -> driver.switchTo().window(windowHandle));
                            driver.switchTo().window(driver.getWindowHandle());
                            System.out.println(driver.getWindowHandles().size());
                        } catch (Exception e) {
                            e.printStackTrace();
                            hasNextPage = false;
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Thread.sleep(10000);
        } finally {
            driver.close();
        }

    }

}