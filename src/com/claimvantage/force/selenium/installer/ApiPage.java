package com.claimvantage.force.selenium.installer;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.claimvantage.force.selenium.PageBase;

public class ApiPage extends PageBase {
    
    private static String NEXT_BUTTON_API = "//div[@class='pbBottomButtons']/input[@title='Next']";
    
    public ApiPage(WebDriver driver) {
        super(driver);
    }

    public void apiPageConfirmation () {
    	((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+driver.findElement(By.xpath(NEXT_BUTTON_API)).getLocation().y+")");
        driver.findElement(By.xpath(NEXT_BUTTON_API)).click();
    }
}
