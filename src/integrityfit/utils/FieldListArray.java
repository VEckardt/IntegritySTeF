/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

import integrityfit.api.IntegrityField;
import java.util.ArrayList;

/**
 * Manages a list of IntegrityField in sorted manner
 *
 * @author veckardt
 */
public class FieldListArray extends ArrayList<IntegrityField> {

    /**
     * SORTED !!! SORTED !!! SORTED !!! SORTED !!! SORTED !!! SORTED !!! SORTED
     * !!!
     */
    public FieldListArray() {
        super();
    }

    public void addNew(IntegrityField fl) {
        if (fl != null) {
            System.out.println("Adding: " + fl.getName());
            addNew(fl.getName(), fl.getDisplayName(), fl.getType(), null);
        }
    }

    private void addNew(String name, String displayName, String type, String datatype) {
        if (findFieldByName(name) == null) {
            super.add(new IntegrityField(name, displayName, type, datatype));
        }
        // System.out.println("FieldListArray/AddNew: "+name+" => "+displayName+" ("+type+")");
    }

    /**
     * Find the value for a given Field Name, if not present it returns null
     *
     * @param fieldName the field you are looking for
     * @return the field value or null
     */
    public String findFieldByName(String fieldName) {
        if (this.isEmpty()) {
            return null;
        }
        for (IntegrityField current : this) {
            if (current.getName().equals(fieldName)) {
                return (current.getName());
            }
        }
        return null;
    }

    /**
     * Returns the table specific column list
     *
     * @return The column list
     */
    public String getColList(String refeId) {
        String cList = "";
        for (IntegrityField current : this) {
            // System.out.println(current.getName());
            if ((refeId == null) || (refeId.contentEquals(""))) {
                cList = cList.concat(current.getDisplayName() + "|");
            } else {
                cList = cList.concat(current.getDisplayName() + "|").replace("Shared ", "");
            }
        }
        return cList;
    }
}
