package com.claimvantage.force.selenium.installer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.claimvantage.force.selenium.PageBase;

public class StartInstallPage extends PageBase{
    
    private static String PKGE_URL;
    private static String PKGE_PW;
    
    private static final String PKGEPW_XPATH = "//input[@type='password']";
    private static final String SUBMIT_XPATH = "//input[@type='submit']";
    
    public StartInstallPage() {
        //TODO:remove
    }
    
    public StartInstallPage(WebDriver driver, String pkgeurl, String pkgepw) {
        super(driver);
        PKGE_URL = pkgeurl;
        PKGE_PW = pkgepw;
    }

    public void navigateToInstallPage() {
        driver.navigate().to(PKGE_URL);
        driver.findElement(By.xpath(PKGEPW_XPATH)).sendKeys(PKGE_PW);
        driver.findElement(By.xpath(SUBMIT_XPATH)).click();
    }
}
