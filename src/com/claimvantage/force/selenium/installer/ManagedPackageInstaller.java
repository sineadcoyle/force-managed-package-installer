package com.claimvantage.force.selenium.installer;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ManagedPackageInstaller extends Task {
    
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    
    private static final Logger LOG =
            Logger.getLogger(ManagedPackageInstaller.class.getPackage().getName());
    
    private String drivertype;
    private String sfurl;
    private String sfun;
    private String sfpw;
    private String pkgeurl;
    private String pkgepw;
    private JSONObject profmap;
    
    private String propertyFailureMessage;
    
    public void execute() throws BuildException {
        try {
            validate();
            setup();
            Installer i = new Installer(drivertype, sfurl, sfun, sfpw, pkgeurl, pkgepw, profmap);
            i.execute();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
        }
    }
    
    public void setDrivertype(String drivertype) {
        this.drivertype = drivertype;
    }
    
    public void setSfurl(String sfurl) {
        this.sfurl = sfurl;
    }
    
    public void setSfun(String sfun)  {
        this.sfun = sfun;
    }
    
    public void setSfpw(String sfpw) {
        this.sfpw = sfpw;
    }
    
    public void setPkgeurl(String pkgeurl) {
        this.pkgeurl = pkgeurl;
    }
    
    public void setPkgepw(String pkgepw) {
        this.pkgepw = pkgepw;
    }
    
    public void setProfmap(String profmap){
        //check if prof.map is included in properties and is not blank
        if (!profmap.isEmpty() && !profmap.equals("${prof.map}")) {
            Object obj = JSONValue.parse(profmap);
            this.profmap = (JSONObject)obj;
        }
    }
    
    public String getFailureMessage() {
        return propertyFailureMessage;
    }

    private void setup() throws Exception {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("ManagedPackageInstaller-" + System.currentTimeMillis() + ".log");

        // Create txt Formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
        
        if (getFailureMessage() != null) {
            System.out.println(getFailureMessage());
            throw new Exception (getFailureMessage());
        }
    }
    
    private void validate() {
        //check that required properties are null, blank or absent from build.properties file
        if (sfurl == null || sfurl.equals("") || sfurl.equals("${sf.url}")) {
            propertyFailureMessage += "\nNo Saleforce URL specified.";
        }
        if (sfun == null || sfun.equals("") || sfun.equals("${sf.un}")) {
            propertyFailureMessage += "\nNo Saleforce username specified.";
        }
        if (sfpw == null || sfpw.equals("") || sfpw.equals("${sf.pw}")) {
            propertyFailureMessage += "\nNo Saleforce password specified.";
        }
        if (pkgeurl == null || pkgeurl.equals("") || pkgeurl.equals("${pkge.url}")) {
            propertyFailureMessage += "\nNo package URL specified.";
        }
    }
}
