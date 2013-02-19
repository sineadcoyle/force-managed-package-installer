package com.claimvantage.force.selenium.installer;

import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ManagedPackageInstaller extends Task {
    
    private String drivertype;
    private String sfurl;
    private String sfun;
    private String sfpw;
    private String pkgeurl;
    private String pkgepw;
    private Map<String, String> profmap = new HashMap<String, String>();
    private String profiles;
    
    public void execute() throws BuildException {
        try {
            validate();
            new Installer(this).execute();
        } catch (BuildException e) {
            throw e;
        } catch (Exception e) {
            log(e.getMessage());
            throw new BuildException(e.getMessage(), e);
        }
    }
    
    public String getDrivertype() {
        return drivertype;
    }
    
    public void setDrivertype(String drivertype) {
        this.drivertype = drivertype;
    }
    
    public String getSfurl() {
        return sfurl;
    }
    
    public void setSfurl(String sfurl) {
        this.sfurl = sfurl;
    }
    
    public String getSfun() {
        return sfun;
    }
    
    public void setSfun(String sfun)  {
        this.sfun = sfun;
    }
    
    public String getSfpw() {
        return sfpw;
    }
    
    public void setSfpw(String sfpw) {
        this.sfpw = sfpw;
    }
    
    public String getPkgeurl() {
        return pkgeurl;
    }
    
    public void setPkgeurl(String pkgeurl) {
        this.pkgeurl = pkgeurl;
    }
    
    public String getPkgepw() {
        return pkgepw;
    }
    
    public void setPkgepw(String pkgepw) {
        this.pkgepw = pkgepw;
    }
    
    public Map<String, String> getProfmap() {
        return profmap;
    }
    
    public String getProfiles() {
        return profiles;
    }
    
    public void setProfiles(String profiles) {
        //check if profiles is included in properties and is not blank
        this.profiles = profiles;
        if (profiles != null && !profiles.isEmpty() && !profiles.equals("${profiles}")) {
            try {
                JSONObject obj = (JSONObject) JSONValue.parse(profiles);
                for (Object key : obj.keySet()) {
                    Object value = obj.get(key);
                    profmap.put((String) key, (String) value);
                }
            } catch (Exception e) {
                throw new BuildException("Failed to parse profiles JSON string " 
                        + profiles 
                        + "; expecting it to be composed of simple key/value pairs but parse failed with error: " 
                        + e.getMessage());
            }
        }
    }

    private void validate() throws BuildException {
        String s = "";
        //check that required properties are null, blank or absent from build.properties file
        if (sfurl == null || sfurl.equals("") || sfurl.equals("${sf.url}")) {
            s += "\nNo Saleforce URL specified.";
        }
        if (sfun == null || sfun.equals("") || sfun.equals("${sf.un}")) {
            s += "\nNo Saleforce username specified.";
        }
        if (sfpw == null || sfpw.equals("") || sfpw.equals("${sf.pw}")) {
            s += "\nNo Saleforce password specified.";
        }
        if (pkgeurl == null || pkgeurl.equals("") || pkgeurl.equals("${pkge.url}")) {
            s += "\nNo package URL specified.";
        }
        if (s.length() > 0) {
            throw new BuildException(s);
        }
    }
}
