package org.example;

import jakarta.mail.MessagingException;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Tikrow {
    ResourceBundle language=Language.LANGUAGE;
    Scanner scanner = new Scanner(System.in);
    public void doLogin(WebDriver driver){
        do {
            WebElement loginInput = new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.visibilityOfElementLocated(By.name("login")));
            System.out.print(language.getString("login")+"\nLogin: ");
            String login = scanner.next();
            loginInput.sendKeys(login);

            WebElement passwordInput = driver.findElement(By.name("password"));
            System.out.print(language.getString("password"));
            char[] password = System.console().readPassword();
            passwordInput.sendKeys(new String(password));

            loginInput.submit();
            System.out.println(language.getString("logining"));

            try {
                WebElement wait = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tikrow-job")));
                break;
            } catch (Exception e) {
                System.out.println(language.getString("something-wrong"));
                driver.navigate().refresh();
            }
        }while(true);

        System.out.println(language.getString("login-successful"));
    }

    public void checkOffers(WebDriver driver, Set<String> listOfHrefs, Set<String> lastAvailable, String mailTo) throws MessagingException, InterruptedException {
        List<WebElement> listOfElements = driver.findElements(By.cssSelector(".tikrow-job"));
        List<String> listOfHrefsTemp = listOfElements.stream().map(webElement -> webElement.findElement(By.tagName("a")).getAttribute("href")).collect(Collectors.toList());
        List<WebElement> numberOfAcceptedOffers = driver.findElements(By.cssSelector(".ribbon-accepted"));
        int numberOfOffers = listOfElements.size()-numberOfAcceptedOffers.size();
        boolean theSame=listOfHrefs.equals(new HashSet<>(listOfHrefsTemp));

        if(numberOfOffers!=0){
            List<String> availableTemp = listOfHrefsTemp.subList(0,numberOfOffers);
            System.out.println(language.getString("available")+availableTemp);
            if(!sameLists(new ArrayList<>(lastAvailable),availableTemp)) {
                System.out.println(language.getString("email-sent"));
                Mail.send(mailTo, "Tikrow", language.getString("new-offer"));
                lastAvailable.clear();
                lastAvailable.addAll(availableTemp);
            }
        }

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        System.out.println(language.getString("offers-number")+(numberOfOffers));
        //System.out.println("Lista stara: "+listOfHrefs);
        //System.out.println("Lista nowa: "+listOfHrefsTemp);
        System.out.println(language.getString("previously-available")+ lastAvailable);
        System.out.print("\n");
        //System.out.println(theSame);

        listOfHrefs.clear();
        listOfHrefs.addAll(listOfHrefsTemp);
    }

    public WebDriver getChromeDriver(){
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--headless");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        return new ChromeDriver(options);
    }

    public WebDriver getFirefoxDriver(){
        FirefoxOptions options=new FirefoxOptions();
        options.addArguments("--headless");
        return new FirefoxDriver(options);
    }

    public boolean sameLists(List<String>list1,List<String>list2){
        for(int i=0;i<list2.size();i++){
            int sum=0;
            for(int j=0;j<list1.size();j++){
                if(list2.get(i).equals(list1.get(j))){
                    sum++;
                }
            }
            if(sum==0){
                return false;
            }
        }

        return true;
    }
}
