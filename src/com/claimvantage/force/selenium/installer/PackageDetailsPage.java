package com.claimvantage.force.selenium.installer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.claimvantage.force.selenium.PageBase;

public class PackageDetailsPage extends PageBase {


    private static String CONTINUE_BUTTON = "//input[@value='Continue']";
    private static String GRANT_ACCESS = "//input[@type = 'checkbox']";
    private static String CONTINUE_BUTTON_POP_UP ="//div[@class='buttons']/input[@value='Continue']";
    private static String THIRD_PARTY_ACCESS_POP_UP = "//h2[text()='Approve Third-Party Access']";
    
    public PackageDetailsPage() {
        //TODO:remove
    }
    
    public PackageDetailsPage(WebDriver driver) {
        super(driver);
    }
    
    public void detailsPageContinue() {
        driver.findElement(By.xpath(CONTINUE_BUTTON)).click();
    }
    
    public boolean thirdPartyAccessPopUpPresent() {
    	return isElementPresent(By.xpath(THIRD_PARTY_ACCESS_POP_UP));
    }
    
    public void detailsPagePopUp () {
        waitForElementPresent(GRANT_ACCESS, 10);
        driver.findElement(By.xpath(GRANT_ACCESS)).click();
        driver.findElement(By.xpath(CONTINUE_BUTTON_POP_UP)).click();
    }
}
