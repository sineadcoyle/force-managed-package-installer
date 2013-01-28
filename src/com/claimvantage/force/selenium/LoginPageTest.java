package com.claimvantage.force.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;


public class LoginPageTest extends TestBase {
    
    private WebDriver driver;
    private LoginPage login;
    
    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        login = new LoginPage(driver);
        driver = login.getLoginPage();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
    
    @Test
    public void testSalesforceLogin() {
        login.loginAs();
        assert(!isElementPresent(driver, By.xpath("//ul[@id='tabBar']/li/a[text() = 'Claims']")));
    }
    
    @Test
    public void testElementPresentTrue() {
        System.out.println(isElementPresent(driver, By.xpath("//div[p[label[@for='username']]]/span/input")));
        assert(isElementPresent(driver, By.xpath("//div[p[label[@for='username']]]/span/input")));
    }
    
    @Test
    public void testElementPresentFalse() {
        System.out.println(isElementPresent(driver, By.xpath("//h3[text()='Something weird']")));
        assert(!isElementPresent(driver, By.xpath("//h3[text()='Something weird']")));
    }
}
