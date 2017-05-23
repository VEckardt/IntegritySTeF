/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import com.mks.api.response.WorkItem;
import java.util.NoSuchElementException;

/**
 * Represents the key Integrity fields for a field
 *
 * @author veckardt
 */
public class IntegrityField {

    private String name;
    private String displayName;
    private String type;
    private String dataType;
    private Boolean showDateTime;
    private String displayPattern;
    private Boolean computedField;

    public IntegrityField(String name) {
        this.name = name;
    }

    public IntegrityField(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    /**
     *
     * @param name
     * @param displayName
     * @param type
     * @param dataType
     */
    public IntegrityField(String name, String displayName, String type, String dataType) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.dataType = dataType;
    }

    public boolean isComputed() {
        return computedField;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Boolean getShowDateTime() {
        return this.showDateTime;
    }

    /**
     *
     * @param wi
     */
    public IntegrityField(WorkItem wi) {
        name = wi.getField("name").getString();
        displayName = wi.getField("displayName").getString();
        type = wi.getField("type").getString();
        try {
            showDateTime = wi.getField("showDateTime").getBoolean();
        } catch (NoSuchElementException ex) {
            showDateTime = false;
        }
        try {
            displayPattern = wi.getField("displayPattern").getValueAsString();
        } catch (NoSuchElementException ex) {
            displayPattern = "";
        }
        try {
            if (wi.getField("computation").getString().length() > 0) {
                computedField = true;
            }
        } catch (NoSuchElementException ex) {
            computedField = false;
        } catch (NullPointerException ex) {
            computedField = false;
        }
    }

    public void showInfo() {
        System.out.println("Name=" + this.name + ", DisplayName=" + this.displayName + ", Type=" + this.type + ", showDateTime=" + this.showDateTime.toString()
                + ", displayPattern=" + displayPattern);
    }

    public String getData() {
        return ("Name=" + this.name + ", DisplayName=" + this.displayName + ", Type=" + this.type + ", showDateTime=" + this.showDateTime.toString()
                + ", displayPattern=" + displayPattern);
    }
}
