/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.ApplicationException;
import com.mks.api.response.Field;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import integrityfit.Config;
import java.util.Date;

/**
 * Represents a list of IntegrityField
 *
 * @author veckardt
 */
public class IntegrityFieldList extends HashMap<String, IntegrityField> {

    // IntegrityAPI ia = new IntegrityAPI();

    public IntegrityFieldList(IntegrityAPI ia) {
        super();
        // if (ia == null)
        // ia.connectCommonSession();

        Command cmd = new Command(Command.IM, "fields");
        cmd.addOption(new Option("fields", "Name,DisplayName,showDateTime,Type,displayPattern,computation"));
        try {
            Response response = ia.executeCmd(cmd);
            ia.debug(response.getCommandString());
            // ResponseUtil.printResponse(response,1,System.out);

            // Loop through all Integrity Fields
            WorkItemIterator wii = response.getWorkItems();
            while (wii.hasNext()) {
                WorkItem wi = wii.next();
                // Loop through all Field Attributes
                this.put(wi.getId(), new IntegrityField(wi));
            }

            ia.log(this.size() + " fields retrieved.");
            // listAll() ;
        } catch (ApplicationException ae) {
            ia.log("Error running addHistory command: ");
            ia.logException(ae);
        } catch (APIException ex) {
            ia.log("Error running addHistory command: ");
            ia.logException(ex);
        }
        // ia.releaseCmdRunner();
    }

    /**
     * Returns the field definition for the given field name
     *
     * @param field the field name
     * @return An Integrity field definition
     */
    public IntegrityField getField(String field) {
        return this.get(field);
    }

    /**
     * Returns the formatted field value
     *
     * @param field A field definition
     * @return The formated value as string
     */
    public String getFormattedValue(Field field) {
        // get the field definition
        IntegrityField ifld = getField(field.getName());
        // ia.log(ifld.displayPattern);
        if (ifld.getType().contentEquals("date")) {
            Date date = field.getDateTime();
            if (date == null) {
                return "(null)";
            }
            if (ifld.getShowDateTime()) {
                return (Config.dfDayTime.format(date));
            } else {
                return (Config.dfDay.format(date));
            }
        } else if (ifld.getType().contentEquals("float")) {
            try {
                return field.getValueAsString().replace(
                        Config.floatDecPointFromTo.charAt(0),
                        Config.floatDecPointFromTo.charAt(1));
            } catch (NullPointerException ae) {
                return "";
            }
        } else {
            return (field.getValueAsString());
        }

    }

    /**
     * Lists all items in the fieldList map
     */
    public void listAll() {
        for (Map.Entry<String, IntegrityField> entry : this.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : "
                    + entry.getValue().getData()+", "+entry.getValue().isComputed());
        }
    }
}
