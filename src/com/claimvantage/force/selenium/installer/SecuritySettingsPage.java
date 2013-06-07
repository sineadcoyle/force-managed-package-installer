package com.claimvantage.force.selenium.installer;

import org.apache.tools.ant.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.claimvantage.force.selenium.PageBase;

public class SecuritySettingsPage extends PageBase {

    private static String SECURITY_SETTINGS = "//td[label[contains(text(), 'Select security settings')]]/input";
    private static String NEXT_BUTTON_SECURITY = "//div[@class='pbBottomButtons']/input[@title='Next']"; 
    
    public SecuritySettingsPage(WebDriver driver, ManagedPackageInstaller task) {
        super(driver, task);
    }
    
    public void securitySettings() {
        waitForElementPresent(SECURITY_SETTINGS, 5);
 
        if (task.getProfmap() != null) {
            driver.findElement(By.xpath(SECURITY_SETTINGS)).click();
            for (String key : task.getProfmap().keySet()) {
                String value = (String) task.getProfmap().get(key);
                driver.findElement(By.xpath("//tr[th[contains(text(), '" + key + "')]]/td/select/option[@value='" + value + "']")).click();
            }
        }

        Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
        if (cp.getBrowserName().equals("chrome")) {
            //chrome bug where button is clicked in centre location (can be on chat icon) - need to scroll, otherwise does not click accurately
            try {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            } catch (Exception e) {
                task.log(e.getMessage(), Project.MSG_ERR);
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        driver.findElement(By.xpath(NEXT_BUTTON_SECURITY)).click();
    }
    
    public void securitySettingsNext() {
        driver.findElement(By.xpath(NEXT_BUTTON_SECURITY)).click();
    }

    public boolean isOnSettingsPage() {
        return isElementPresent(By.xpath(SECURITY_SETTINGS));
    }
    
    public boolean isMapPresent() {
        return !(task.getProfmap() == null);
    }
}
