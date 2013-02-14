package com.claimvantage.force.selenium.installer;

import org.apache.tools.ant.Project;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.claimvantage.force.selenium.LoginPage;

public class Installer {

    private LoginPage login;
    private StartInstallPage sip;
    private UpdateDetailsPage udp;
    private PackageDetailsPage pdp;
    private ApiPage api;
    private SecuritySettingsPage ssp;
    private EndInstallPage eip;

    private WebDriver driver;
    
    public Installer(ManagedPackageInstaller task) {
        setDriverType(task);
        login = new LoginPage(driver, task);
        sip = new StartInstallPage(driver, task);
        pdp = new PackageDetailsPage(driver, task);
        udp = new UpdateDetailsPage(driver, task);
        api = new ApiPage(driver, task);
        ssp = new SecuritySettingsPage(driver, task);
        eip = new EndInstallPage(driver, task);
    }
    
    public void execute(ManagedPackageInstaller task) {
        login.getLoginPage(); //navigate to login page
        task.log("Logging in.", Project.MSG_INFO);
        login.loginAs(); //login
        sip.navigateToInstallPage(); //navigate to install package url with password
        if (sip.passwordIsRequired()) {
            sip.enterPackagePassword();
        }
        pdp.detailsPageContinue(); //details page continue
        
        //upgrade install
        if (udp.isUpdate()) {
            udp.detailsPageConfirmationUpgrade();
        } else {
            //fresh install
            if (pdp.thirdPartyAccessPopUpPresent()) {
                //needed for api access
                pdp.detailsPagePopUp();
            }
        }
        
        api.apiPageConfirmation();
        if (ssp.isOnSettingsPage() && ssp.isMapPresent()) {
            ssp.securitySettings();
        } else if (ssp.isOnSettingsPage()) {
            ssp.securitySettingsNext();
        }
        eip.endInstall();
    }
    
    private void setDriverType(ManagedPackageInstaller task) {
        String drivertype = task.getDrivertype();
        if ("firefox".equalsIgnoreCase(drivertype)) {
            driver = new FirefoxDriver();
        } else if ("chrome".equalsIgnoreCase(drivertype)) {
            task.log(System.getProperty("os.name"), Project.MSG_VERBOSE);
            if ((System.getProperty("os.name").contains("Mac"))) { //if Mac OS, use chromedriver for Mac
                task.log("Using Mac OS, using chromedriver for Mac.", Project.MSG_INFO);
                System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver");
            } else {                                                    //otherwise use the .exe
                task.log("Using chromedriver.exe", Project.MSG_INFO);
                System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
            }
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions"); //removes Chrome extensions for this browser instance
            driver = new ChromeDriver(options);
        } else {
            task.log("No browser specified. Defaulting to Firefox webdriver.", Project.MSG_WARN);
            driver = new FirefoxDriver();
        }

        //print name and version of browser
        Capabilities caps = ((RemoteWebDriver)driver).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getVersion();
        task.log(browserName + " " + browserVersion, Project.MSG_INFO);
    }
}
