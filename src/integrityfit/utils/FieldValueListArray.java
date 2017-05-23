/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

import integrityfit.api.IntegrityFieldValue;
import java.util.ArrayList;

/**
 * Provides a list object for Integrity field values
 *
 * @author veckardt
 */
public class FieldValueListArray extends ArrayList<IntegrityFieldValue> // SORTED !!! SORTED !!! SORTED !!! SORTED !!! SORTED !!! SORTED !!! SORTED !!!
{

    /**
     * Add a new entry to the field list
     *
     * @param fieldName the field name
     * @param fieldValue the field value
     */
    public void add(String fieldName, String fieldValue) {
        super.add(new IntegrityFieldValue(fieldName, fieldValue));
    }

    /**
     * Adds a value only if it is not yet in list
     *
     * @param fieldName
     * @param fieldValue
     */
    public void addNew(String fieldName, String fieldValue) {
        // if not yet in list

        if (findValueByName(fieldName) == null) {
            // System.out.println("fieldName => " + fieldName + " fieldValue => "+fieldValue);
            this.add(fieldName, fieldValue);
        }
    }

    /**
     * Find the value for a given Field Name, if not present it returns null
     *
     * @param fieldName the field you are looking for
     * @return the field value or null
     */
    public String findValueByName(String fieldName) {
        return findValueByName(fieldName, null);
    }

    private String findValueByName(String fieldName, String defaultText) {
        // System.out.println ("fieldName => "+fieldName);
        if (this.isEmpty()) {
            return defaultText;
        }
        for (IntegrityFieldValue current : this) {
            if (current.getName() == null) {
                return null;
            }
            if (current.getName().equals(fieldName)) {
                return (current.getValue());
            }
        }
        return defaultText;
    }

    /**
     * Sets the value for a Field in the list
     *
     * @param fieldName
     * @param value
     */
    public void setValue(String fieldName, String value) {
        if (this.isEmpty()) {
            return;
        }
        for (IntegrityFieldValue current : this) {
            if (current.getName().equals(fieldName)) {
                current.setValue(value);
                return;
            }
        }
    }

    /**
     * List all Items stored in the list
     */
    public void listAll() {
        for (IntegrityFieldValue current : this) {
            System.out.println(current.getName() + " => " + current.getValue());
        }
    }
}
