/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit;

import integrityfit.utils.FitTestCase;
import integrityfit.api.IntegrityAPI;
import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.ApplicationException;
import com.mks.api.response.Field;
import com.mks.api.response.Item;
import com.mks.api.response.ItemList;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;
import integrityfit.api.IntegrityField;
import integrityfit.api.IntegrityFieldList;
import integrityfit.api.IntegrityFieldValue;
import integrityfit.utils.ExceptionHandler;
import integrityfit.api.IntegrityItem;
import integrityfit.api.IntegrityItemSort;
import integrityfit.api.IntegritySQL;
import integrityfit.api.IntegrityTypeList;
import integrityfit.utils.FieldListArray;
import integrityfit.utils.FieldValueListArray;
import integrityfit.utils.QueryUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Main class used by the STeF Retriever
 *
 * @author veckardt
 */
public class IntegrityTransactions extends IntegrityAPI {

    // List of all change entries in Integrity
    private final List<IntegrityItem> changeList = new ArrayList<>();
    // List of all fields defined in Integrity
    private IntegrityFieldList fieldList; //  = new IntegrityFieldList(this);
    // Object to collect the Fit Test Case text entries
    private final FitTestCase fitTestCase = new FitTestCase(null);
    // Simple list to hold the types used in this run
    private final IntegrityTypeList typeList = new IntegrityTypeList();
    // dynamic determined valiables
    private final FieldValueListArray variables = new FieldValueListArray();
    // some more private variables
    private String queryName;
    private Date startDate;
    private Date endDate;
    private String hostname;
    private String port;
    private int cntVar = 0;
    private final String AddVariablesHere = "Add Variables Here";
    private final String variablesHeader = "Use this table to specify items already in Integrity\n";
    private String itemIDs;

    /**
     * Constructor, sets the 5 main class variables
     *
     * @param startDate
     * @param endDate
     * @param queryName
     * @param hostname
     * @param port
     */
    public IntegrityTransactions(
            Date startDate,
            Date endDate,
            String queryName,
            String hostname,
            String port,
            String itemIDs) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.queryName = queryName;
        this.hostname = hostname;
        this.port = port;
        this.itemIDs = itemIDs;
    }

    /**
     * Main execution method
     *
     * @param testCaseFile the FitNesse file to write at
     * @param overwriteFlag forces overwrite the fit fixture if file already
     * exists
     */
    public Boolean readHistoryAndGenFitNesseTc(String testCaseFile, Boolean overwriteFlag, int option) {

        // Step 1: dont do anything if it's not possible/allowed to save
        if (!this.fitTestCase.testCaseFileSave(testCaseFile, overwriteFlag)) {
            return false;
        }
        // Step 2: Run Query, and Check Items History. Add if appropriate
        if (option == 1) {
            getChangedItems(this.itemIDs);
        } else {
            getChangedItems(null);
        }
        // Step 3: Set the document class for all changes
        this.typeList.setDocumentClass(this.changeList);
        // Step 4: Build up the column output list with respect to black list
        
        fieldList = new IntegrityFieldList(this);
        
        constructColList();
        // Step 5: Build the fitNesse output
        genFitNesse();
        // Step 6: Generate and add the first table with the list of variables
        genFixtureForVariables();
        // Step 7: Write the output to a file
        if (this.fitTestCase.writeTestCase(testCaseFile)) {
            // Step 8: Put it also into the log
            log("\nGenerated Fixture:");
            log(this.fitTestCase.getTestCase());
            return true;
        }
        return false;
    }

    /**
     * If the change record is dated to the creation time, its a creation
     *
     * @param wiDate The date to check
     * @param item An Integrity item
     * @return True if creation, otherwise false
     */
    private boolean isCreateIssue(Date wiDate, Item item) {
        // return (wiDate.equals(item.getField("MKSIssueModifiedDate").getDateTime()));
        return item.getId().contentEquals("0");
    }

    public int getNumberOfChangesIdentified() {
        if ((this.changeList == null) || (this.changeList.isEmpty())) {
            return 0;
        }
        return this.changeList.size();
    }

    /**
     * Gets the list of changed items within the specified time frame This
     * version uses the SQL trick to get exactly what is needed only the items
     * which were changed during that time frame Therefore this procedure is
     * much more faster
     */
    private void getChangedItems(String itemIDs) {

        connectCommonSession();
        try {
            // startDate and endDate given
            if (isEmpty(itemIDs)) {
                // A bit tricky, but this is what I want :)
                // IntegritySQL is = new IntegritySQL(this); 
                List<String> li;
                li = IntegritySQL.getItemsInTimeFrame(this, this.startDate, this.endDate);
                for (int i = 0; i < li.size(); i++) {
                    // System.out.println("==> "+li.get(i));
                    log(" Checking item " + li.get(i) + " for changes ...");
                    // adds the change information to changeList
                    int hC = addHistory(li.get(i), this.startDate, this.endDate, null); // wiType.getField("documentClass").getValueAsString());
                    log("   " + hC + " entries added.");
                };
            } else {
                Command cmd = new Command(Command.IM, "issues");
                // cmd.addOption(new Option("QueryDefinition", "((subquery[" + this.queryName + "]) and (" + QueryUtils.getSubQuery(itemIDs) + "))"));
                cmd.addOption(new Option("QueryDefinition", "((" + QueryUtils.getSubQuery(itemIDs) + "))"));
                Response response = executeCmd(cmd);
                WorkItemIterator wii = response.getWorkItems();
                while (wii.hasNext()) {
                    WorkItem wi = wii.next();

                    log(" Checking item #" + wi.getId() + " for changes ...");
                    // adds the change information to changeList
                    int hC = addHistory(wi.getId(), this.startDate, this.endDate, null); // wiType.getField("documentClass").getValueAsString());
                    log("   " + hC + " entries added.");
                    // break;
                }

            }
            // 

        } catch (APIException ae) {
            ExceptionHandler eh = new ExceptionHandler(ae);
            log(eh.getMessage());
            // return;
        }
        // 
        releaseCmdRunner();
        // sort by timestamp ascending
        IntegrityItemSort ssbm = new IntegrityItemSort();
        Collections.sort(this.changeList, ssbm);
    }

    /**
     * Gets the list of changed items within the specified time frame
     */
    /*
     * private void getChangedItems2(String itemIDs) {

     connectCommonSession();

     try {
     // im issues --user=veckardt --hostname=AVL --queryDefinition="((subquery[AVL All Releases]) and (field[ID]=41))"
     Command cmd = new Command(Command.IM, "issues");

     if (isEmpty(itemIDs)) {
     // TODO: it would be nice to have the time option here too
     String startStrg = new SimpleDateFormat(Config.dtDayFormat).format(this.startDate);
     String endStrg = new SimpleDateFormat(Config.dtDayFormat).format(this.endDate);
     cmd.addOption(new Option("QueryDefinition", "((subquery[" + this.queryName + "]) and (field[Modified Date]between " + startStrg + " and " + endStrg + "))"));
     } else {
     cmd.addOption(new Option("QueryDefinition", "((subquery[" + this.queryName + "]) and (" + QueryUtils.getSubQuery(itemIDs) + "))"));
     }
     // 
     Response response = execute(cmd);

     WorkItemIterator wii = response.getWorkItems();
     while (wii.hasNext()) {
     WorkItem wi = wii.next();

     log(" Checking item #" + wi.getId() + " for changes ...");
     // adds the change information to changeList
     int hC = addHistory(wi.getId(), this.startDate, this.endDate, null); // wiType.getField("documentClass").getValueAsString());
     log("   " + hC + " entries added.");
     // break;

     }
     } catch (APIException ae) {
     ExceptionHandler eh = new ExceptionHandler(ae);
     log(eh.getMessage());
     // return;
     }
     releaseCmdRunner();

     // sort by timestamp ascending
     IntegrityItemSort ssbm = new IntegrityItemSort();
     Collections.sort(this.changeList, ssbm);
     } */
    /**
     * Adds the information from the shared item to the base item change record
     *
     * @param itemId the shared item id
     * @param modifiedDate the date to check
     */
    private void addSharedItemInfo(String itemId, Date modifiedDate) {
        Command cmd = new Command(Command.IM, "viewissue");
        cmd.addOption(new Option("showHistory"));
        // show History Ascending
        cmd.addOption(new Option("showHistoryAscending"));
        cmd.addSelection(itemId);
        try {
            Response response = this.executeCmd(cmd);
            debug(response.getCommandString());
            // ResponseUtil.printResponse(response,1,System.out);
            debug("*************************************************************");

            WorkItem wi = response.getWorkItem(itemId);
            // References

            ItemList iHist = (ItemList) wi.getField("MKSIssueHistory").getList();
            // this loops through all change sets = items
            for (Iterator it = iHist.iterator(); it.hasNext();) {
                Item change = (Item) it.next();
                log(" Checking Modification at " + change.getField("MKSIssueModifiedDate").getDateTime().toString() + " ...");

                // Only add shared item history if its happend within the same second
                if (modifiedDate.compareTo(change.getField("MKSIssueModifiedDate").getDateTime()) == 0) {
                    // 
                    log(" Shared Item with change found: " + change.getContext());
                    // Iterator for changed fields
                    for (Iterator itf = change.getFields(); itf.hasNext();) {
                        Field f = (Field) itf.next();
                        if (f.getName().contains("Shared ")) {
                            debug("Field Name = '" + f.getName() + "', " + f.getDataType() + ", ");
                            // TODO : else to add
                            if (!f.getDataType().contentEquals("com.mks.api.response.Item")) {
                                debug("Content => " + f.getString());
                                // Put into the latest added change item also the Shared Item Information
                                IntegrityItem ii = this.changeList.get(this.changeList.size() - 1);
                                ii.sharedFieldList.put(f.getName(), f.getString());
                            }
                        }
                    }
                    // if something found, we can stop here
                    break;
                }
                debug("" + this.changeList.size());
            }
        } catch (ApplicationException ae) {
            log("Error running addSharedItemInfo command: ");
            logException(ae);
        } catch (APIException ex) {
            log("Error running addSharedItemInfo command: ");
            logException(ex);
        }
    }

    /**
     * Adds the item history within the time frame to the 'changeList'
     *
     * @param itemID item to check
     * @param StartDate start date
     * @param EndDate end date
     */
    private int addHistory(String itemID, Date startDate, Date endDate, String modelClass) {

        int histCounter = 0;
        // Command.
        Command cmd = new Command(Command.IM, "viewissue");
        cmd.addOption(new Option("showHistory"));
        // showHistoryAscending
        cmd.addOption(new Option("showHistoryAscending"));
        cmd.addSelection(itemID);
        // Execute disconnect
        try {
            Response response = this.executeCmd(cmd);
            debug(response.getCommandString());
            // ResponseUtil.printResponse(response,1,System.out);
            debug("*************************************************************");

            WorkItem wi = response.getWorkItem(itemID);
            // References
            if (!wi.getField("Type").getValueAsString().contains("Shared")) {

                ItemList iHist = (ItemList) wi.getField("MKSIssueHistory").getList();
                // this loops through all change sets = items
                for (Iterator it = iHist.iterator(); it.hasNext();) {
                    Item change = (Item) it.next();
                    debug(">> Loop ChangeSet");
                    debug("Check: " + change.getField("MKSIssueModifiedDate").getDateTime().toString());

                    // Only add item history if it is between start and end date
                    if ((startDate == null && endDate == null) || (startDate.compareTo(change.getField("MKSIssueModifiedDate").getDateTime()) <= 0) && (endDate.compareTo(change.getField("MKSIssueModifiedDate").getDateTime()) >= 0)) {
                        // this step will also determine the Shared Item ID (if any)
                        IntegrityItem ii = new IntegrityItem(wi, change, modelClass);
                        this.changeList.add(ii);
                        histCounter++;
                        // log("ii.getRefeId() => "+ii.getRefeId());
                        if (ii.getRefeId() != null) {
                            addSharedItemInfo(ii.getRefeId(), ii.modifiedDate);
                        }
                    }

                    /*
                     * for (Iterator fli = im.getFields(); fli.hasNext();) {
                     Field fl = (Field)fli.next();
                     if (validField(fl)) {
                     debug (fl.getName() + "\t=> " +fl.getValueAsString() + "\t"+fl.getDataType());
                     }
                     }
                     * */
                    debug(" " + this.changeList.size() + " change sets identified.");
                }
            }
        } catch (ApplicationException ae) {
            log("Error running addHistory command: ");
            logException(ae);
        } catch (APIException ex) {
            log("Error running addHistory command: ");
            logException(ex);
        }
        return histCounter;
    }

    /**
     * Creates a list of columns touched as max for an item
     */
    private void constructColList() {
        String itemId = "0";
        FieldListArray colList = null;
        IntegrityItem iiref;

        // Dont do anything if empty
        if (changeList.isEmpty()) {
            return;
        }

        // take the first item in the list
        iiref = (IntegrityItem) changeList.get(0);
        // Loop through all change records
        for (IntegrityItem ii : changeList) {
            // new Item ID starts
            if (!ii.getId().contentEquals(itemId)) {
                if (!itemId.contentEquals("0")) {
                    iiref.colList = colList;
                    // log(iiref.columns);
                }
                // remeber the first changeItem in the list for an item 
                iiref = ii;
                itemId = ii.getId();
                // Init new column list
                colList = new FieldListArray();
                // Track the field where a new item starts
            }

            // Build the column list
            for (Iterator fli = ii.item.getFields(); fli.hasNext();) {
                Field fld = (Field) fli.next();
                if (FieldBlackList.validField(ii.getType(), this.typeList.getDocClass(ii.getType()), fld)) {
                    // add the full field information from Integrity Setup
                    IntegrityField iField = fieldList.getField(fld.getName());
                    if (iField != null && !iField.isComputed()) {
                        colList.addNew(iField);
                        System.out.println(iField.getName()+ " added.");
                    }
                }
            }
            // add in addition also the shared fields to the end
            if (!ii.sharedFieldList.isEmpty()) {
                for (Map.Entry<String, String> entry : ii.sharedFieldList.entrySet()) {
                    // add the full field information from Integrity Setup
                    colList.addNew(fieldList.getField(entry.getKey()));
                }
            }
            log(" Column list contains " + colList.size() + " entries for item " + ii.getId() + " change record " + ii.item.getId());
        }
        iiref.colList = colList;

    }

    /**
     * Returns the variable set for the item ID. If not available in stack yet,
     * it will be created fresh and added to the stack
     *
     * @param value the value, here any item id
     * @return the variable for the value
     */
    private String getVariable(String value) {
        // log ("value => "+value);
        String variable = variables.findValueByName(value);
        if (variable == null) {
            cntVar++;
            variables.addNew(value, "$" + Config.vaPrefix + cntVar);
            variable = "$" + Config.vaPrefix + cntVar;
        }
        return variable;
    }

    /**
     * If the given field is a relationship, then try to map to a variable
     * otherwise just replay what is given
     *
     * @param ifd An Integrity Field
     * @param value The value found
     * @return The value or the variable already translated into
     */
    private String validateRelField(IntegrityField ifd, String value) {
        // log ("validateRelField => "+ifd.getName());
        if ((ifd.getType().equals("relationship")) && (value != null)) {
            return getVariable(value);
        }
        return (value == null ? "" : value);
    }

    /**
     * Returns the fixture for the current set of variables and puts the same
     * into the output string at place 'AddVariablesHere'
     */
    private void genFixtureForVariables() {
        log(" Variables list contains " + variables.size() + " entries.");
        
        // needed for getItemType

        String variablesData = variablesHeader;
        variablesData = variablesData.concat("|");
        // | Integrity |  |
        // | Type | State | Summary | findIssue? |
        // | Project | In Development | ARE3035 | $IDP1= |
        // | Project | In Development | ARE3005 | $IDP2= |

        // if at least one row, add 'Integrity' fixture
        // otherwise add 'Comment' fixture
        variablesData = variables.size() > 0 ? variablesData.concat("Integrity") : variablesData.concat("Comment");
        variablesData = variablesData.concat("| " + this.hostname + ":" + this.port + "||\n");
        variablesData = variablesData.concat("| Type | State | Summary | ID | findItem? |\n");

        // Loop through all fields
        for (IntegrityFieldValue ifv : variables) {
            String itype = getItemType(ifv.getName());
            if (isEmpty(itype)) {
                variablesData = variablesData.concat("|" + "|||" + ifv.getName() + "|" + ifv.getValue() + "=|\n");
            } else {
                variablesData = variablesData.concat("|" + itype + "|||" + ifv.getName() + "|" + ifv.getValue() + "=|\n");
            }
        }
        // releaseCmdRunner();
        // replace the placeholder in string
        fitTestCase.replaceInTestCase(AddVariablesHere, variablesData);
    }

    /**
     * Creates a test file for FitNesse
     */
    private void genFitNesse() {
        // Construct Table for FitNesse
        Boolean isCreate = false;
        FieldListArray colList = null;

        // Leave funtion if empty
        if (changeList.isEmpty()) {
            return;
        }

        // First part of FitTestCase
        fitTestCase.addToTestCase("!define TEST_SYSTEM {slim}\n");
        fitTestCase.addToTestCase(AddVariablesHere);

        String itemId = "0";
        // Loop through all change records
        for (IntegrityItem ii : changeList) {
            Item changeItem = ii.item;
            // we are separating the items in different blocks
            // only needed if new item starts
            if (!ii.getId().contentEquals(itemId)) {
                // remember the collist for the current item
                colList = ii.colList;
                // if at least one column is filled
                if ((ii.colList != null) && (ii.colList.size()) > 0) {
                    itemId = ii.getId();
                    // Begin Column Headers
                    fitTestCase.addToTestCase("\n");
                    fitTestCase.addToTestCase("\nChanges made " + ii.modifiedDate + " for " + ii.getType() + " (" + ii.getIdAndRefeId() + "):\n");
                    // Print table header and column header
                    fitTestCase.addToTestCase("|Integrity|" + this.hostname + ":" + this.port + "||\n");
                    fitTestCase.addToTestCase("|Module|Command|");
                    if (isCreateIssue(ii.creationDate, changeItem)) {
                        fitTestCase.addToTestCase("Type|");  // Only required when creation in progress
                        isCreate = true;
                    } else {
                        isCreate = false;
                    }
                    // Print rest of header
                    fitTestCase.addToTestCase(colList.getColList(ii.getRefeId()));
                    fitTestCase.addToTestCase("Selection|Result?|Message?|Value?|\n");
                }
            }

            // some data to progress?
            if ((colList != null) && (!colList.isEmpty())) {

                String newToTestCase = "";
                // Loop through the list of columns and print data if appropriate
                for (IntegrityField fv : colList) {
                    String valueToAdd;
                    try {
                        // Search for the change in this field, if it exists
                        Field fl = changeItem.getField(fv.getName());
                        valueToAdd = fieldList.getFormattedValue(fl);

                    } catch (NoSuchElementException se) {
                        debug("no value for: " + fv.getName());
                        // perhaps a shared item value to add?
                        valueToAdd = ii.sharedFieldList.get(fv.getName());
                    }
                    valueToAdd = validateRelField(fv, valueToAdd);
                    // add value and slash:
                    newToTestCase = newToTestCase.concat(valueToAdd + "|");
                }
                // make sure we do not anything if the column list is empty
                if (!isEmpty(newToTestCase.replaceAll("\\|", ""))) {
                    // Print first two columns for the data
                    if (isCreateIssue(ii.creationDate, changeItem)) {
                        fitTestCase.addToTestCase(this.typeList.getCreateCmd(ii.getType()));
                    } else if (isCreate) {
                        fitTestCase.addToTestCase("|im|editissue||");  // one more for type
                    } else {
                        fitTestCase.addToTestCase("|im|editissue|");
                    }

                    fitTestCase.addToTestCase(newToTestCase);

                    // Expected result is 0
                    if (isCreateIssue(ii.creationDate, changeItem)) {
                        // Check and get a variable for the return value
                        String var = getVariable(ii.getId());
                        // put it to the end
                        fitTestCase.addToTestCase("|0||" + var + "=|\n");
                    } else {
                        // column selection
                        fitTestCase.addToTestCase("" + getVariable(ii.getId()) + "|0|||\n");
                    }
                }
            }
        }
    }

//    @Override
//    public void log(String text) {
//         TestAppController.putLog(5, text);
//    }
}
