/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit;

import integrityfit.api.IntegrityAPI;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides application configuration data and property handling
 *
 * @author veckardt
 */
public class Config {

    public static Locale locale = Locale.getDefault();
    
    // different dateformats used in this app
    // public static String dtDayTimeFormat = DateUtil.DEFAULT_DATETIMEONLY_FORMAT; // "dd.MM.yyyy HH:mm:ss";
    public static String dtDayTimeFormatSQL = "yyyy-MM-dd HH:mm:ss"; // 2013-12-02 12:58:13.887
    // public static String dtDayFormat = DateUtil.DEFAULT_DATEONLY_FORMAT;

    // public static String dtDayTimeFormat = "MMM d, yyyy  hh:mm:ss a";
    // public static String dtDayFormat =  "MMM d, yyyy";        
    
    public static DateFormat dfDay = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    public static DateFormat dfDayTime = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault());
    public static DateFormat dfTime = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.getDefault());

    // public static String dtTimeFormat = dtDayTimeFormat.replace(dtDayFormat, "").trim();
    
    
    public static String floatDecPointFromTo = ",.";
    // Integrity Standard fields
    public static String fnID = "ID";
    public static String fnType = "Type";
    public static String fnSummary = "Summary";
    public static String fnState = "State";
    public static String fnText = "Text";
    // Variablen Prefix
    public static String vaPrefix = "VAR";
    // Basepath where to store the test cases in
    public String fitBasePath = "C:\\IntegrityFit\\FitNesseRoot\\FrontPage\\IntegrityTesting\\DemoContent\\GeneratedTests\\";
    // name of property file
    private String propertyFile = "integrityfit.properties";
    private Properties properties = new Properties();

    /**
     * Loads the current settings from
     */
    public Config() {
        
        IntegrityAPI.log("Locale is set to:    '" + locale + "'");

        IntegrityAPI.log("Date and Time format in use:");

//        IntegrityAPI.log("DATEONLY_FORMAT:     '" + dtDayFormat + "'");
//        IntegrityAPI.log("DATETIMEONLY_FORMAT: '" + dtDayTimeFormat + "'");
//        IntegrityAPI.log("TIMEONLY_FORMAT:     '" + dtTimeFormat + "'");

        IntegrityAPI.log("dfDay     1: " + dfDay.format(new Date()));
        IntegrityAPI.log("dfDayTime 2: " + dfDayTime.format(new Date()));
        IntegrityAPI.log("dfTime    2: " + dfTime.format(new Date()));

        // float flt = 1; flt=flt/2;
        // IntegrityAPI.log(Float.toString(flt));
        // if (Float.toString(flt).contentEquals("0.5")) {
        // locale = Locale.ENGLISH;
        //     floatDecPointFromTo = ",,";  // no change
        // }
        // else {
        // locale = Locale.GERMAN;
        //     floatDecPointFromTo = ".,";  //swap
        // }

        BufferedInputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(propertyFile));
            properties.load(stream);
            stream.close();
            // String sprache = properties.getProperty("lang");
        } catch (FileNotFoundException ex) {
            // doesnt matter
            return;
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Set a value (overwrite)
     *
     * @param property
     * @param value
     */
    public void Set(String property, String value) {
        properties.setProperty(property, value);
    }

    /**
     * Gets the value for one property
     *
     * @param property the property
     * @return the value
     */
    public String Get(String property) {
        return (properties.getProperty(property));
    }

    /**
     * Saves the current properties to integritytest.properties
     */
    public void Save() {
        FileOutputStream out = null;
        try {
            // properties.setProperty("fitnessepath", "C:\\Tools\\FitNesseRoot\\FrontPage\\IntegrityTesting\\GeneratedTests");   // 
            out = new FileOutputStream(propertyFile);
            properties.store(out, null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
