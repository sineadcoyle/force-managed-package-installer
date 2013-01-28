package com.claimvantage.force.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class LoginPage extends PageBase{
    private static final String UN_XPATH = "//div[p[label[@for='username']]]/span/input";
    private static final String PW_XPATH = "//div[p[label[@for='password']]]/span/input";
    private static final String LOGIN_XPATH = "//input[@class = 'loginButton']";
    
    private static String SF_URL;
    private static String SF_USERNAME;
    private static String SF_PASSWORD;
    
    public LoginPage(WebDriver driver) {
    	SF_URL = "https://login.salesforce.com";
        SF_USERNAME = "example@testing.com";
        SF_PASSWORD = "XXXXX";
    }
    
    public LoginPage(WebDriver driver, String sfurl, String sfun, String sfpw) {
        super(driver);
        SF_URL = sfurl;
        SF_USERNAME = sfun;
        SF_PASSWORD = sfpw;
    }
    
    
    public void loginAs() {
        loginAs(SF_USERNAME, SF_PASSWORD);
    }
    
    public void loginAs(String u, String p) {
        driver.findElement(By.xpath(UN_XPATH)).sendKeys(u);
        driver.findElement(By.xpath(PW_XPATH)).sendKeys(p);
        driver.findElement(By.xpath(LOGIN_XPATH)).click();
        waitForElementPresent("//ul[@id='tabBar']/li/a[text() = 'Home']", 5);
    }
    
    public WebDriver getLoginPage() {
        driver.get(SF_URL);
        return driver;
    }
}
