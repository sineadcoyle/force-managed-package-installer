package com.claimvantage.force.selenium;

import org.apache.tools.ant.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.claimvantage.force.selenium.installer.ManagedPackageInstaller;

public class PageBase {
    
    protected WebDriver driver;
    protected ManagedPackageInstaller task;
    
    //extra constructor for LoginPageTest
    public PageBase(WebDriver driver) {
        this.driver = driver;
    }
    
    public PageBase (WebDriver driver, ManagedPackageInstaller task) {
        this.driver = driver;
        this.task = task;
    }
    
    protected void waitForBrowser(int delay) {
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException ex) {
            task.log(ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
    
    protected boolean waitForElementPresent(String xpath, int wait) {
        long startTime = System.currentTimeMillis();
        while (startTime + wait > System.currentTimeMillis()) {
            try {
                task.log("Waiting for xpath element - " + xpath, Project.MSG_VERBOSE);
                if (isElementPresent(By.xpath(xpath))) return true;
                Thread.sleep(1000);
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new RuntimeException(e.getMessage() + " - could not find element " + xpath, e);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
        return false;
    }
    
    public boolean isElementPresent(By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
