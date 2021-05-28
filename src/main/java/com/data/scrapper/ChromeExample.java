package com.data.scrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChromeExample {
    private static final String URL="https://learning.oreilly.com/accounts/login-check/";
    public static void main(String[] args) throws InterruptedException {

        //Setting system properties of ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/Users/sivanandareddy/Softwares/chromedriver");
        //Creating an object of ChromeDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        try{
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
            List<WebElement> booksLink = driver.findElements(By.tagName("article"));
            System.out.println("showing book links:"+ booksLink.size());
            for (var bookLink: booksLink){
                //System.out.println(bookLink.getText());
                bookLink.findElement(By.tagName("a")).click();
                Thread.sleep(2000);//detail-toc
                driver.switchTo().window(driver.getWindowHandle());
                //List<WebElement> chapterLinks = driver.findElement(By.className("detail-toc")).findElements(By.tagName("li"));
                List<WebElement> chapterLinks = driver.findElements(By.className("toc-level-1"));
                System.out.println("Chapters: "+ chapterLinks.size());
                for(var chapterLink : chapterLinks){
                    //System.out.println(chapterLink.getText() +"*****"+  chapterLink.findElement(By.tagName("a")).getText());
                }
                driver.switchTo().window(driver.getWindowHandle());
                driver.findElement(By.linkText("Preface")).click();

               // <a href="/library/view/treasury-markets-and/9780470827604/fpref.xhtml" class="t-chapter" tabindex="49">Preface</a>
                System.out.println(chapterLinks.get(1).findElements(By.tagName("a")).get(1).getText());
               // chapterLinks.get(1).findElements(By.tagName("a")).get(1).("href").click();
                /*Actions act =  new Actions(driver);
                act.moveToElement(chapterLinks.get(1).findElement(By.tagName("a"))).click().perform();*/
                //chapterLinks.get(0).findElement(By.tagName("a")).click();
                driver.switchTo().window(driver.getWindowHandle());
                String chapterContent = driver.findElement(By.id("sbo-rt-content")).getText();
                System.out.println(chapterContent);
                System.out.println("**************************************");
                System.out.println("\n\n\n");;

            /*for ( var chapterLink: chapterLinks){
                //chapterLink.click();
                chapterLink.findElement(By.tagName("a")).click();
                driver.switchTo().window(driver.getWindowHandle());
                String chapterContent = driver.findElement(By.id("sbo-rt-content")).getText();
                System.out.println(chapterContent);
                System.out.println("**************************************");
                System.out.println("\n\n\n");
            }*/

            }
        } catch (Exception e){
            e.printStackTrace();
            Thread.sleep(10000);
        }finally {
            //driver.close();
        }

    }

}