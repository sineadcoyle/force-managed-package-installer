package com.claimvantage.force.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import com.claimvantage.force.selenium.installer.ManagedPackageInstaller;

public class LoginPage extends PageBase{
    private static final String UN_XPATH = "//div[p[label[@for='username']]]/span/input";
    private static final String PW_XPATH = "//div[p[label[@for='password']]]/span/input";
    private static final String LOGIN_XPATH = "//input[@class = 'loginButton']";
    private static final String HOME_TAB = "//ul[@id='tabBar']/li/a[text() = 'Home']";
    
    private String SF_URL;
    private String SF_USERNAME;
    private String SF_PASSWORD;
    
    //Used for LoginPageTest
    public LoginPage(WebDriver driver) {
        super(driver);
        SF_URL = "https://login.salesforce.com";
        SF_USERNAME = "example@testing.com";
        SF_PASSWORD = "XXXXX";
    }
    
    public LoginPage(WebDriver driver, ManagedPackageInstaller task) {
        super(driver, task);
        SF_URL = task.getSfurl();
        SF_USERNAME = task.getSfun();
        SF_PASSWORD = task.getSfpw();
    }
    
    
    public void loginAs() {
        loginAs(SF_USERNAME, SF_PASSWORD);
    }
    
    public void loginAs(String u, String p) {
        driver.findElement(By.xpath(UN_XPATH)).sendKeys(u);
        driver.findElement(By.xpath(PW_XPATH)).sendKeys(p);
        driver.findElement(By.xpath(LOGIN_XPATH)).click();
        
        if (!isElementPresent(By.xpath(HOME_TAB))) {
            throw new RuntimeException("Failed to log in with username: " + task.getSfun()
                    + ". Please check the validity your login credentials.");
        }
    }
    
    public WebDriver getLoginPage() {
        driver.get(SF_URL);
        return driver;
    }
}
