# Introduction #

This ant task has been designed to automate the process of installing a managed package on Salesforce.com using Selenium WebDriver. Selenium is used as Salesforce do not provide an API.

This has been tested with API version 27 Spring '13. Later releases of the Salesforce API may break the ant task and it might need to be fixed.

It was built using JDK (Java Development Kit) version 1.6.

# Instructions #

  * Have Firefox or Chrome installed - Firefox 18 and Chrome 24 have been tested with this package. Firefox is the preferred browser to run with this jar.
  * Set up any custom profiles on the Salesforce org that you wish to use
  * Create an Ant script with the following required properties included:
    * **sfurl** - Salesforce login URL, e.g., `https://login.salesforce.com`
    * **sfun** -Salesforce username
    * **sfpw** -  Salesforce password (not a concatenation of Salesforce password and security token, as simply logging into browser)
    * **pkgeurl** - URL of package to be installed - e.g., `https://login.salesforce.com/packaging/installPackage.apexp?p1=123456`
> and the following optional properties:
    * **drivertype** - Choose browser type - "firefox" or "chrome". If none specified, will default to Firefox
    * **pkgepw** - Package password
    * **profiles** - a JSON object profile map to the security settings for each profile, e.g., {"Profile1":"SecuritySettings1", "Profile2":"SecuritySettings2"}
> Here is an example of an Ant script - Note: to escape double quotes in the profiles property, an XML character reference is used - &quot;. Also note that other characters within strings, such as &, must be replaced by character references - [Ant character references](http://www.jguru.com/faq/view.jsp?EID=721755) :

```
<?xml version="1.0" encoding="UTF-8"?>
<project name="project" default="run-installer-task" basedir =".">

     <target name="run-installer-task">
            <taskdef name="sfInstall"
                classname="com.claimvantage.force.selenium.installer.ManagedPackageInstaller"
            	classpath="ant-force-managed-package-installer-1.2.jar"/>
            <sfInstall
            	drivertype="chrome"
            	sfurl="https://login.salesforce.com"
            	sfun="example@testing.com"
            	sfpw="password"
            	pkgeurl="https://login.salesforce.com/packaging/installPackage.apexp?p1=123456"
            	pkgepw="password"
            	profiles="{&quot;Profile 1&quot;:&quot;Security Settings 1&quot;,&quot;Profile 2&quot;:&quot;Security Settings 2&quot;}"/>
     </target>

</project>
```

> Another way of doing this is by using a property file to set the properties - please see the Ant documentation for instructions.

  * Run package
    * Open cmd.exe (Windows) or Terminal (Mac) to run from the command line.
    * Navigate to the file directory with the jar file in it - e.g. (for Mac), cd ~username/documents/workspace/force-managed-package-installer.
    * Type ant
    * If you want to add properties by the command line, use the syntax -Dpropertyname=propertyvalue after ant
    * Press enter

Please note: If the user is prompted for an activation code, or if any task dialog appears after login to the Salesforce account, the installer will fail. The dialog will have to be dealt with manually (e.g., an activation code might need to be entered) before running the installer again.