package com.claimvantage.force.selenium.installer;

import java.util.logging.Logger;
import net.sf.json.JSONObject;
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

    private static final Logger LOG =
            Logger.getLogger(ManagedPackageInstaller.class.getPackage().getName());
    
    public Installer(String drivertype, String sfurl, String sfun, String sfpw, String pkgeurl, String pkgepw, JSONObject profmap) {
        setDriverType(drivertype);
        login = new LoginPage(driver, sfurl, sfun, sfpw);
        sip = new StartInstallPage(driver, pkgeurl, pkgepw);
        pdp = new PackageDetailsPage(driver);
        udp = new UpdateDetailsPage(driver);
        api = new ApiPage(driver);
        ssp = new SecuritySettingsPage(driver, profmap);
        eip = new EndInstallPage(driver);
    }
    
    public void execute() {
        login.getLoginPage(); //navigate to login page
        login.loginAs(); //login
        LOG.info("Logging in.");
        sip.navigateToInstallPage(); //navigate to install package url with password
        pdp.detailsPageContinue(); //details page continue
        
        //upgrade install
        if (udp.isUpdate()) {
            udp.detailsPageConfirmationUpgrade();
        }
        
        //fresh install
        else {
            //needed for api access
            if (pdp.thirdPartyAccessPopUpPresent()) {
                pdp.detailsPagePopUp();
            }
        }
        
        api.apiPageConfirmation();
        if((ssp.isOnSettingsPage()==true)&&(ssp.isMapPresent()==true)) {
            ssp.securitySettings();
        }
        else if (ssp.isOnSettingsPage()==true) {
            ssp.securitySettingsNext();
        }
        eip.endInstall();
    }
    
    private void setDriverType(String drivertype) {
        if (drivertype.equals("firefox")) {
        	driver = new FirefoxDriver();
            if (!((RemoteWebDriver)driver).getCapabilities().getVersion().contains("16.0")) {
            	LOG.warning("Selenium Webdriver not fully compatible with Firefox above version 16.");
            }
        }
        
        else if (drivertype.equals("chrome")) {
        	System.out.println(System.getProperty("os.name"));
        	if ((System.getProperty("os.name").contains("Mac"))) { //if Mac OS, use chromedirver for MAc
            	System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver");
        	}
        	else {													//otherwise use the .exe
        		System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
        	}
        	ChromeOptions options = new ChromeOptions();
        	options.addArguments("--disable-extensions"); //removes Chrome extensions for this browser instance
        	driver = new ChromeDriver(options);
        }
        
        else {
        	LOG.warning("No browser specified. Defaulting to Firefox webdriver.");
        	driver = new FirefoxDriver();
        }

        //print name and version of browser for debugging
        Capabilities caps = ((RemoteWebDriver)driver).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getVersion();
        System.out.println(browserName + " " + browserVersion);
    }
}
