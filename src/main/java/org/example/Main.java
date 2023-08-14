package org.example;

import jakarta.mail.MessagingException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, MessagingException {
        byte languageNumber=Language.selectLanguage(); //language selection menu 1. english 2. polish
        Language.setLANGUAGE(languageNumber); //sets the language
        ResourceBundle language=Language.LANGUAGE; //static variable from Language class to local variable in order to make easier later in sout

        Tikrow tikrow=new Tikrow(); //object Tikrow which contains necessary functions
        Scanner scanner = new Scanner(System.in);

        System.out.println(language.getString("start"));


        WebDriver driver = tikrow.getChromeDriver(); //creates driver with special options
        System.out.print(language.getString("initial-info"));
        System.console().readLine();
        System.out.println(language.getString("wait")+"\n");

        driver.navigate().to("https://partner.tikrow.com/");
        tikrow.doLogin(driver); //login on site tikrow.com

        System.out.print(language.getString("mail"));
        String mailTo = scanner.next(); //takes emails to which notification should be sent e.g. example@example.com or example1@example.com,example2@example.com

        System.out.print("\n");
        Set<String> listOfHrefs = new HashSet<>();
        Set<String> lastAvailable = new HashSet<>();

        while(true) {
            tikrow.checkOffers(driver,listOfHrefs,lastAvailable,mailTo); //checks offers on tikrow

            Thread.sleep(40000); //wait milliseconds
            driver.navigate().refresh();
            try {
                //waits for element on site, if element will not appear in duration then throws exception
                WebElement waitTimer = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tikrow-job")));
            }
            catch (TimeoutException e){
                e.printStackTrace();
            }
        }
    }
}
