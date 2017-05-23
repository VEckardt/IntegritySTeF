/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.Item;
import com.mks.api.response.ItemList;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class to test the IntegrityItem class
 *
 * @author veckardt
 */
public class IntegrityItemTest {

    private static List<IntegrityItem> changeList = new ArrayList<IntegrityItem>();
    private static IntegrityAPI ia = new IntegrityAPI();

    public List<IntegrityItem> getChangeList() {
        return changeList;
    }

    public static void addModificationsFromItem(String itemId) {

        // Command.
        Command cmd = new Command(Command.IM, "viewissue");
        cmd.addOption(new Option("showHistory"));
        // showHistoryAscending
        cmd.addOption(new Option("showHistoryAscending"));
        cmd.addSelection(itemId);
        try {
            Response response = ia.executeCmd(cmd);
            // ResponseUtil.printResponse(response,1,System.out);
            WorkItem wi = response.getWorkItem(itemId);
            // References

            ItemList iHist = (ItemList) wi.getField("MKSIssueHistory").getList();
            // this loops through all change sets = items
            for (Iterator it = iHist.iterator(); it.hasNext();) {
                Item change = (Item) it.next();
                IntegrityItem ii = new IntegrityItem(wi, change, null);
                changeList.add(ii);
            }
        } catch (APIException ex) {
            ia.log("Error running addModificationsFromItem command: ");
            ia.logException(ex);
        }

    }

    @BeforeClass
    public static void setUpClass() {

        ia.connect("localhost", 7001, "testuser", "password");

        // Adds the following items to the changeList Array:
        addModificationsFromItem("446");
        addModificationsFromItem("454");
        addModificationsFromItem("442");
        addModificationsFromItem("620");
    }

    @AfterClass
    public static void tearDownClass() {
        changeList.clear();
        ia.releaseCmdRunner();
        // ia.disconnect();
    }

    @Test
    public void testGetCount() {
        int expResult = 19;
        int result = changeList.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTypeShortName method, of class IntegrityItem.
     */
    @Test
    public void testGetTypeShortName() {
        System.out.println("getTypeShortName");
        for (IntegrityItem instance : changeList) {
            String expResult = "";
            if (instance.getType().contentEquals("Specification")) {
                expResult = "AS";
            }
            if (instance.getType().contentEquals("Specification Document")) {
                expResult = "ASD";
            }
            if (instance.getType().contentEquals("Defect")) {
                expResult = "D";
            }
            String result = instance.getTypeShortName();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of isGreater method, of class IntegrityItem.
     */
    @Test
    public void testIsGreater1_1() {
        System.out.println("isGreater");
        IntegrityItem first = changeList.get(0);
        IntegrityItem second = changeList.get(1);
        boolean expResult = false;
        boolean result = first.isGreater(second);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsGreater1_2() {
        System.out.println("isGreater");
        IntegrityItem first = changeList.get(0);
        IntegrityItem second = changeList.get(1);
        boolean expResult = true;
        boolean result = second.isGreater(first);
        assertEquals(expResult, result);
    }

    /**
     * Test of isGreater2 method, of class IntegrityItem.
     */
    @Test
    public void testIsGreater2_1() {
        System.out.println("isGreater2");
        IntegrityItem ii = changeList.get(0);
        IntegrityItem instance = changeList.get(0);
        int expResult = 0;
        int result = ii.isGreater2(instance);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsGreater2_2() {
        System.out.println("isGreater2");
        IntegrityItem ii = changeList.get(0);
        IntegrityItem instance = changeList.get(1);
        int expResult = -1;
        int result = ii.isGreater2(instance);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsGreater2_3() {
        System.out.println("isGreater2");
        IntegrityItem ii = changeList.get(1);
        IntegrityItem instance = changeList.get(0);
        int expResult = 1;
        int result = ii.isGreater2(instance);
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class IntegrityItem.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        for (IntegrityItem instance : changeList) {
            String expResult = "";
            if (instance.getId().contentEquals("446")) {
                expResult = "Specification";
            }
            if (instance.getId().contentEquals("454")) {
                expResult = "Specification";
            }
            if (instance.getId().contentEquals("442")) {
                expResult = "Specification Document";
            }
            if (instance.getId().contentEquals("620")) {
                expResult = "Defect";
            }
            String result = instance.getType();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getRefeId method, of class IntegrityItem.
     */
    @Test
    public void testGetRefeId() {
        System.out.println("getRefeId");
        for (IntegrityItem instance : changeList) {
            String expResult = "";
            if (instance.getId().contentEquals("446")) {
                expResult = "447";
            }
            if (instance.getId().contentEquals("454")) {
                expResult = "455";
            }
            if (instance.getId().contentEquals("442")) {
                expResult = null;
            }
            if (instance.getId().contentEquals("620")) {
                expResult = null;
            }
            String result = instance.getRefeId();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getIdAndRefeId method, of class IntegrityItem.
     */
    @Test
    public void testGetIdAndRefeId() {
        System.out.println("getIdAndRefeId");
        for (IntegrityItem instance : changeList) {
            String expResult = "";
            if (instance.getId().contentEquals("446")) {
                expResult = "446/447";
            }
            if (instance.getId().contentEquals("454")) {
                expResult = "454/455";
            }
            if (instance.getId().contentEquals("442")) {
                expResult = "442";
            }
            if (instance.getId().contentEquals("620")) {
                expResult = "620";
            }
            String result = instance.getIdAndRefeId();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getId method, of class IntegrityItem.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        for (IntegrityItem instance : changeList) {
            String expResult = "";
            if (instance.getId().contentEquals("446")) {
                expResult = "446";
            }
            if (instance.getId().contentEquals("454")) {
                expResult = "454";
            }
            if (instance.getId().contentEquals("442")) {
                expResult = "442";
            }
            if (instance.getId().contentEquals("620")) {
                expResult = "620";
            }
            String result = instance.getId();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getContext method, of class IntegrityItem.
     *
     * @Test public void testGetContext() { System.out.println("getContext");
     * for (IntegrityItem instance : changeList) { String expResult=""; switch
     * (instance.getId()) { case "446": expResult = "446"; break; case "454":
     * expResult = "454"; break; case "442": expResult = "442"; break; case
     * "620": expResult = "620"; break; } String result = instance.getContext();
     * assertEquals(expResult, result); } }
     */
    /**
     * Test of getField method, of class IntegrityItem.
     */
    @Test
    public void testGetField() {
        System.out.println("getField");
        String string = "MKSIssueModifiedDate";
        for (IntegrityItem instance : changeList) {
            System.out.println(">> testGetField() " + instance.getId() + ": '" + instance.item.getId() + "'");
            String expResult = "";
            if (instance.getId().contentEquals("446")) {
                if (instance.item.getId().contentEquals("0")) {
                    expResult = "Thu May 24 14:21:05 CEST 2012";
                }
                if (instance.item.getId().contentEquals("10")) {
                    expResult = "Mon Nov 18 21:56:27 CET 2013";
                }
            } else if (instance.getId().contentEquals("454")) {
                if (instance.item.getId().contentEquals("0")) {
                    expResult = "Sun May 27 16:55:49 CEST 2012";
                }
                if (instance.item.getId().contentEquals("10")) {
                    expResult = "Sun May 27 21:36:10 CEST 2012";
                }
            } else if (instance.getId().contentEquals("442")) {
                if (instance.item.getId().contentEquals("0")) {
                    expResult = "Tue May 22 23:01:03 CEST 2012";
                }
                if (instance.item.getId().contentEquals("2")) {
                    expResult = "Mon Jun 18 14:18:25 CEST 2012";
                }
                if (instance.item.getId().contentEquals("3")) {
                    expResult = "Mon Jun 18 14:38:05 CEST 2012";
                }
                if (instance.item.getId().contentEquals("4")) {
                    expResult = "Mon Jun 18 18:09:06 CEST 2012";
                }
                if (instance.item.getId().contentEquals("5")) {
                    expResult = "Tue Jun 19 15:20:17 CEST 2012";
                }
            }
            // System.out.println("expResult => "+expResult);
            String result = instance.getField(string).getValueAsString();
            assertEquals(expResult, result);
        }
    }
}