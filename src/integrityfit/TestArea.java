/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit;

import integrityfit.api.IntegrityAPI;
import integrityfit.api.IntegrityField;
import integrityfit.api.IntegrityFieldList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import mks.util.DateUtil;

/**
 * Just a testing class, not in production at all
 *
 * @author veckardt
 */
public class TestArea {

    static IntegrityTransactions it;

    public static void main(String[] args) {

        IntegrityAPI.log(DateUtil.getDateDisplayString(new Date()));
        IntegrityAPI.log(DateUtil.DEFAULT_DATEONLY_FORMAT);
        IntegrityAPI.log(DateUtil.DEFAULT_DATETIMEONLY_FORMAT);

        IntegrityAPI.log(DateUtil.DEFAULT_DATETIMEZ_FORMAT);
        IntegrityAPI.log(DateUtil.DEFAULT_DATEZ_FORMAT);
        IntegrityAPI.log(DateUtil.DEFAULT_TIMEZONEONLY_FORMAT);
        IntegrityAPI.log(DateUtil.getDateDisplayString(new Date(), DateUtil.DEFAULT_DATEONLY_FORMAT));
        // IntegrityAPI.log(DateUtil
        // IntegrityAPI.log(DateUtil
    }

    public static void main1(String[] args) {

        Locale locale = Locale.getDefault();
        System.out.println(locale);

        SimpleDateFormat f = null;
        Date today = new Date();
        f = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH);
        System.out.println(f.format(today) + " - " + f.toPattern());


        today = new Date();
        f = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
        System.out.println(f.format(today) + " - " + f.toPattern());
        System.out.println(System.getProperty("user.country"));
        System.out.println(System.getProperty("user.language"));
        IntegrityFieldList ifl = new IntegrityFieldList(it);
        IntegrityField ifld = ifl.getField("VE_Allow_Scope");
        // ifld.showInfo();
        // ifl.listAll();
        // IntegritySQL is = new IntegritySQL(); 
        try {
            String startString = "December 03, 2013";
            Date startDate;
            startDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(startString);

            List<String> li = null;
            // li = IntegritySQL.getItemsInTimeFrame(is, startDate, new Date()); 
            for (int i = 0; i < li.size(); i++) {
                System.out.println("==> " + li.get(i));
            };
        } catch (ParseException ex) {
            Logger.getLogger(TestArea.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main2(String[] args) {


        try {
            // Calendar mydate = new GregorianCalendar();
            String startString = "November 16, 2013";
            Date startDate;
            startDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(startString);

            String endString = "November 17, 2013";
            Date endDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(endString);

            System.out.println(startDate.toString());
            System.out.println(endDate.toString());

            // ia.addHistory("618", null, null);

            IntegrityTransactions ia = new IntegrityTransactions(
                    startDate,
                    endDate,
                    "HistoryFilter",
                    "localhost",
                    "7001",
                    "");
            ia.readHistoryAndGenFitNesseTc("C:\\Tools\\FitNesseRoot\\FrontPage\\IntegrityTesting\\GeneratedTests\\TestSession\\content.txt", true, 1);

            // log ("------------------------------------------------------");
        } catch (ParseException ex) {
            Logger.getLogger(TestArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void log(String text) {
        System.out.println(text);
    }
}
