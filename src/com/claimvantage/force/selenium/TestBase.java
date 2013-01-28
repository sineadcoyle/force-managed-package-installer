package com.claimvantage.force.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class TestBase {
    
    protected boolean isElementPresent(WebDriver driver, By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } 
        catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
        catch (org.openqa.selenium.TimeoutException ex) {
            return false;
        }
    }
}
