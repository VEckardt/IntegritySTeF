/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class to test the QueryUtils class
 *
 * @author veckardt
 */
public class QueryUtilsTest {

    public QueryUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of addIfNull method, of class QueryUtils.
     */
    @Test
    public void testAddIfNull1() {
        System.out.println("testAddIfNull1");
        String input = "";
        String prefix = " and ";
        String string = "DEF";
        String expResult = "DEF";
        String result = QueryUtils.addIfNull(input, prefix, string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    @Test
    public void testAddIfNull2() {
        System.out.println("testAddIfNull2");
        String input = "ABC";
        String prefix = " and ";
        String string = "DEF";
        String expResult = "ABC a nd DEF";
        String result = QueryUtils.addIfNull(input, prefix, string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of getSubQuery method, of class QueryUtils.
     */
    @Test
    public void testGetSubQuery1() {
        System.out.println("getSubQuery");
        String itemString = "1, 2,3 ";
        String expResult = "(field[ID]=1) or (field[ID]=2) or (field[ID]=3)";
        String result = QueryUtils.getSubQuery(itemString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    @Test
    public void testGetSubQuery2() {
        System.out.println("getSubQuery");
        String itemString = "1";
        String expResult = "(field[ID]=1)";
        String result = QueryUtils.getSubQuery(itemString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
}