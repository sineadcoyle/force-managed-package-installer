package com.claimvantage.force.selenium.installer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.claimvantage.force.selenium.PageBase;

public class StartInstallPage extends PageBase{
    
    private static final String PKGEPW_XPATH = "//input[@type='password']";
    private static final String SUBMIT_XPATH = "//input[@type='submit']";
    private static final String PASSWORD_REQUIRED_HEADING = "//h3[text()='Password Required']";

    public StartInstallPage(WebDriver driver, ManagedPackageInstaller task) {
        super(driver, task);
    }

    public void navigateToInstallPage() {
        driver.navigate().to(task.getPkgeurl());
    }
    
    public void enterPackagePassword() {
        driver.findElement(By.xpath(PKGEPW_XPATH)).sendKeys(task.getPkgepw());
        driver.findElement(By.xpath(SUBMIT_XPATH)).click();
    }
    
    public boolean passwordIsRequired() {
        return (isElementPresent(By.xpath(PASSWORD_REQUIRED_HEADING)));
    }
}
