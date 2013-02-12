package com.claimvantage.force.selenium.installer;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.claimvantage.force.selenium.PageBase;

public class SecuritySettingsPage extends PageBase {

    private static String SECURITY_SETTINGS = "//td[label[contains(text(), 'Select security settings')]]/input";
    private static String NEXT_BUTTON_SECURITY = "//div[@class='pbBottomButtons']/input[@title='Next']"; 
    private static JSONObject PROFILE_MAP;
    private Map<String, String> profileMap;
    
    public SecuritySettingsPage(WebDriver driver, JSONObject profmap) {
        super(driver);
        PROFILE_MAP = profmap;
    }
    
    public void securitySettings() {
        try {
            waitForElementPresent(SECURITY_SETTINGS, 2);
            driver.findElement(By.xpath(SECURITY_SETTINGS)).click();
              if (!(PROFILE_MAP.isEmpty())) {
                Iterator<Map.Entry<String, String>> i = profileMap.entrySet().iterator();
                while (i.hasNext()) {
                    Entry<String, String> currentEntry = (Entry<String, String>)i.next();
                    String key = (String) currentEntry.getKey();
                    String value = (String) currentEntry.getValue();
                    driver.findElement(By.xpath("//tr[th[contains(text(), '" + key + "')]]/td/select/option[@value='" + value + "']")).click();
                }
            }
            Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
            if (cp.getBrowserName().equals("chrome")) {
                //chrome bug where button is clicked in centre location (can be on chat icon) - need to scroll, otherwise does not click accurately
                try {
                    ((JavascriptExecutor) driver).executeScript(
                            "window.scrollTo(0, document.body.scrollHeight);");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            driver.findElement(By.xpath(NEXT_BUTTON_SECURITY)).click();
        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    
    public void securitySettingsNext() {
        driver.findElement(By.xpath(NEXT_BUTTON_SECURITY)).click();
    }

    public boolean isOnSettingsPage() {
        return (isElementPresent(By.xpath(SECURITY_SETTINGS)));
    }
    
    public boolean isMapPresent() {
        return !(PROFILE_MAP == null);
    }
}
