package com.claimvantage.force.selenium.installer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.claimvantage.force.selenium.PageBase;

public class UpdateDetailsPage extends PageBase {
    
    private static String HANDLE_COMPONENTS_HEADER = "//h2[contains(text(), 'Handle Component Name Conflicts')]";
    private static String HANDLE_COMPONENTS_RADIO_BUTTON = "//td[label[contains(text(), 'Block installation and list conflicts')]]/input";
    private static String NEXT_BUTTON_HANDLE_COMPONENTS = "//input[@title='Next']";
    
    public UpdateDetailsPage() {
        //TODO:remove
    }
    
    public UpdateDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void detailsPageConfirmationUpgrade() {
        driver.findElement(By.xpath(HANDLE_COMPONENTS_RADIO_BUTTON)).click();
        driver.findElement(By.xpath(NEXT_BUTTON_HANDLE_COMPONENTS)).click();
    }
    
    public String getHandleComponentsHeader () {
        return HANDLE_COMPONENTS_HEADER;
    }
    
    public boolean isUpdate() {
        return (isElementPresent(By.xpath(getHandleComponentsHeader())));
    }
}
