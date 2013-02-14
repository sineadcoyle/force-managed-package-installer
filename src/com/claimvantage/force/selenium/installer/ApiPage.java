package com.claimvantage.force.selenium.installer;

import org.apache.tools.ant.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.claimvantage.force.selenium.PageBase;

public class ApiPage extends PageBase {
    
    private static String NEXT_BUTTON_API = "//div[@class='pbBottomButtons']/input[@title='Next']";
    private static String NEWER_VERSION_INSTALLED_HEADER = "//td[contains(text(), 'newer version')]";
    
    public ApiPage(WebDriver driver, ManagedPackageInstaller task) {
        super(driver, task);
    }

    public void apiPageConfirmation () {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+driver.findElement(By.xpath(NEXT_BUTTON_API)).getLocation().y+")");
            driver.findElement(By.xpath(NEXT_BUTTON_API)).click();
        } catch (NoSuchElementException e) {
            if (isElementPresent(By.xpath(NEWER_VERSION_INSTALLED_HEADER))) {
                task.log("Newer version of package already installed", Project.MSG_ERR);
            }
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
