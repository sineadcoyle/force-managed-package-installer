package com.claimvantage.force.selenium.installer;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.claimvantage.force.selenium.PageBase;

public class EndInstallPage extends PageBase {
    private static String INSTALL_BUTTON = "//input[@title='Install']";
    private static String INSTALLING_PACKAGE = "//h1[text()='Processing']";
    private static String INSTALLED_INTEGRATION = "//h2[contains(text(), 'Installed')]";
    private static final Logger LOG =
            Logger.getLogger(ManagedPackageInstaller.class.getPackage().getName());
    
    public EndInstallPage(WebDriver driver) {
        super(driver);
    }
    
    public void endInstall () {
        driver.findElement(By.xpath(INSTALL_BUTTON)).click();
        installed();
    }
    
    public void installed() {
        if (isElementPresent(By.xpath(INSTALLING_PACKAGE)) || isElementPresent(By.xpath(INSTALLED_INTEGRATION))) {
            LOG.info("Package installed.");
            driver.quit();
        }
    }
}
