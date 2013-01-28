package com.claimvantage.force.selenium;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageBase {
    
    protected WebDriver driver;
    
    public PageBase() {
        
    }
    
    public PageBase (WebDriver driver) {
        this.driver = driver;
    }
    
    protected void waitForBrowser(int delay) {
        try {
            Thread.sleep(delay * 1000);
        } 
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    protected boolean waitForElementPresent(String xpath, int wait) {
        driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
        
        boolean found = false;
        int count = 0;
        
        while (found != true && count < 5) {
            try {
                driver.findElement(By.xpath(xpath));
                return true;
            }
            
            catch(NoSuchElementException e) {
                System.err.print(e.getMessage());
            }
            
            count++;
        }
        return false;
    }
    
    public boolean isElementPresent(By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } 
        catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
}
