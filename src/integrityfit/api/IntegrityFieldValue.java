/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

/**
 * A class extending the IntegrityField by a value
 *
 * @author veckardt
 */
public class IntegrityFieldValue extends IntegrityField {

    private String value;

    public IntegrityFieldValue(String name, String value) {
        super(name);
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
