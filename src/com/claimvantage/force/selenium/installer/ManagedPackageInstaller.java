package com.claimvantage.force.selenium.installer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

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
	
	public void execute() throws BuildException {
		try {
			setup();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
		}
		Installer i = new Installer(drivertype, sfurl, sfun, sfpw, pkgeurl, pkgepw, profmap);
        i.execute();
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
	
	public void setProfmap(String profmap) {
		if (!profmap.equals("${prof.map}")) {
			this.profmap = (JSONObject) JSONSerializer.toJSON(profmap);
		}
		else {
			profmap = null;
		}
	}

	private void setup() throws SecurityException, IOException {
		Logger logger = Logger.getLogger("");
        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("ManagedPackageInstaller-" + System.currentTimeMillis() + ".log");

        // Create txt Formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);  
	}
}
