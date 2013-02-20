package com.claimvantage.force.selenium.installer;

import org.apache.tools.ant.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.claimvantage.force.selenium.PageBase;

public class EndInstallPage extends PageBase {
    private static String INSTALL_BUTTON = "//input[@title='Install']";
    private static String ORGANISATION_LOCKED = "//th[contains(text(), 'Locked')]";
    
    public EndInstallPage(WebDriver driver, ManagedPackageInstaller task) {
        super(driver, task);
    }
    
    public void endInstall () {
        driver.findElement(By.xpath(INSTALL_BUTTON)).click();
        task.log("Last step of installer package process", Project.MSG_VERBOSE);
        
        if (isElementPresent(By.xpath(ORGANISATION_LOCKED))) {
            task.log("Package install failed with username: " + task.getSfun(), Project.MSG_ERR);
            throw new RuntimeException("Package install failed. Org locked for changes due to installation of another package. Please try again when other package finished installing.");
        } else {
            task.log("Package installed.", Project.MSG_INFO);
            driver.quit();
        }
    }
}
