package com.claimvantage.force.selenium.installer;

import org.apache.tools.ant.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.claimvantage.force.selenium.PageBase;

public class EndInstallPage extends PageBase {
    private static String INSTALL_BUTTON = "//input[@title='Install']";
    private static String INSTALLING_PACKAGE = "//h1[text()='Processing']";
    private static String INSTALLED_INTEGRATION = "//h2[contains(text(), 'Installed')]";
    
    public EndInstallPage(WebDriver driver, ManagedPackageInstaller task) {
        super(driver, task);
    }
    
    public void endInstall () {
        driver.findElement(By.xpath(INSTALL_BUTTON)).click();
        task.log("Last step of installer package process", Project.MSG_VERBOSE);
        
        if (isElementPresent(By.xpath(INSTALLING_PACKAGE)) || isElementPresent(By.xpath(INSTALLED_INTEGRATION))) {
            task.log("Package installed.", Project.MSG_INFO);
            driver.quit();
        } else {
            task.log("Package install failed with username: " + task.getSfun(), Project.MSG_ERR);
            throw new RuntimeException("Package install failed. Org locked for changes due to installation of package.");
        }
    }
}
