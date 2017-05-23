/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class to test the IntegrityTypeList class
 *
 * @author veckardt
 */
public class IntegrityTypeListTest {

    // IntegrityItemTest iit = new IntegrityItemTest();
    private IntegrityTypeList tl = new IntegrityTypeList();
    private static IntegrityItemTest iit = new IntegrityItemTest();

    public IntegrityTypeListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        iit.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() {
        iit.tearDownClass();
    }

    /**
     * Test of getDocClass method, of class IntegrityTypeList.
     */
    @Test
    public void testGetDocClass() {
        System.out.println("getDocClass");
        tl.setDocumentClass(iit.getChangeList());
        for (IntegrityItem instance : iit.getChangeList()) {
            String expResult = "";
            if (instance.getType().contentEquals("Specification")) {
                expResult = "node";
            }
            if (instance.getType().contentEquals("Specification Document")) {
                expResult = "segment";
            }
            if (instance.getType().contentEquals("Defect")) {
                expResult = "none";
            }
            String result = tl.getDocClass(instance.getType());
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getCreateCmd method, of class IntegrityTypeList.
     */
    @Test
    public void testGetCreateCmd() {
        System.out.println("getCreateCmd");
        tl.setDocumentClass(iit.getChangeList());
        for (IntegrityItem instance : iit.getChangeList()) {
            String expResult = "";
            if (instance.getType().contentEquals("Specification")) {
                expResult = "|im|createcontent|Specification|";
            }
            if (instance.getType().contentEquals("Specification Document")) {
                expResult = "|im|createsegment|Specification Document|";
            }
            if (instance.getType().contentEquals("Defect")) {
                expResult = "|im|createissue|Defect|";
            }
            String result = tl.getCreateCmd(instance.getType());
            assertEquals(expResult, result);
        }
    }
}