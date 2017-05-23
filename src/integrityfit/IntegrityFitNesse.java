/*
 * Contains basic commands to access and work with Integrity API
 * 
 */
package integrityfit;

import integrityfit.api.IntegrityAPI;
import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.APIInternalError;
import com.mks.api.response.Field;
import com.mks.api.response.Item;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;
import com.mks.api.util.ResponseUtil;

import com.ptc.services.common.tm.TestResult;
import com.ptc.services.common.tm.TestResultList;
import integrityfit.api.IntegrityFieldValue;
import integrityfit.api.IntegritySQL;
import integrityfit.utils.FieldValueListArray;
import integrityfit.utils.ExceptionHandler;
import integrityfit.utils.Timer;
import integrityfit.utils.FileHandler;
import integrityfit.utils.LastMessage;
import integrityfit.utils.OSCommandHandler;
import integrityfit.utils.QueryUtils;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * Provides methods called by the Integrity wrapper class
 *
 * @author veckardt
 */
public class IntegrityFitNesse extends IntegrityAPI {

    
    public IntegrityFitNesse() {
        super (System.getenv(), "IntegrityFitNesse");
    }
    /**
     * Constructor to connect to Integrity using the common session properties
     *
     * @param hostAndPort the hostname alone or host:port
     * @param port the port
     * @param username the username
     * @param password the password
     */
    public IntegrityFitNesse(String hostAndPort, int port, String username, String password) {
        super(null, null);
        int pos = hostAndPort.indexOf(":");
        if (pos > 0) {
            port = Integer.parseInt(hostAndPort.substring(pos + 1));
            hostAndPort = hostAndPort.substring(0, pos);
        }
        if (!isEmpty(username)) {
            pos = username.indexOf(":");
            if (pos > 0) {
                password = username.substring(pos + 1);
                username = username.substring(0, pos);
            }
        }
        connectCommonSession(hostAndPort, port, username, password);
        log("Connected to " + hostAndPort + ":" + port + " with " + username + ".");
    }
    private LastMessage lm = new LastMessage();
    private String fieldValue;
    // Integrity Fields
    // Pre defined general Fields, always available 
    private String id;
    private String parentId;
    // String description;
    private String selection;
    private String command;
    private String module;
    private String fieldName;
    private int count = 0;
    //for testing
    private String verdict;
    private String annotation;
    private String stepVerdict;
    private String stepID;
    // Two local lists:
    // Holds the fields added to --fields=
    private FieldValueListArray fl = new FieldValueListArray();
    // Holds all test resuts to use them when executing edit result
    private TestResultList trl = new TestResultList();
    // Holds the fixed param list
    private FieldValueListArray pl = new FieldValueListArray();
    private static final String[] resultFilter = {""};
    private String fileSize;
    private String fileName;
    private String fieldList;
    private Map<String, String> addEnv = new HashMap<>();
    private Map<String, String> fieldTypes = new HashMap<>();

    // IntegrityFieldList integrityFieldList = new IntegrityFieldList(this);
    /**
     * Init internal variables for each call (is called by module)
     */
    public void init(String module) {
        this.fl.clear();
        this.pl.clear();
        debug("Parameter and Fields cleared.");
        this.selection = "";
        this.module = module;
        this.command = "";
        this.fieldValue = "";
        this.fieldName = "";
        this.count = 0;
        this.id = "";
        this.parentId = null;
        // Testing Part 
        this.verdict = "";
        this.annotation = "";
        this.stepVerdict = "";
        this.stepID = "";
        this.fileSize = "";
        this.fileName = "";
    }

    /**
     * set Routines
     */
    public void setModule(String module) {
        init(module);
        if ("im".equalsIgnoreCase(module)) {
            this.module = Command.IM;
        }
        if ("si".equalsIgnoreCase(module)) {
            this.module = Command.SI;
        }
        if ("tm".equalsIgnoreCase(module)) {
            this.module = Command.TM;
        }
        if ("aa".equalsIgnoreCase(module)) {
            this.module = Command.AA;
        }

    }

    public String getLastMessage() {
        return lm.getLastMessage();
    }

    public void setLastMessage(String message) {
        lm.setLastMessage(message);
    }

    public void setField(String fieldname, String value) {
        fl.add(fieldname, value);
    }

    public void setParam(String field, String value) {
        if (!isEmpty(value)) {
            pl.add(field, value);
        }
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setId(String text) {
        this.id = text;
    }

    public void setFileName(String text) {
        this.fileName = text;
    }

    public void setParentId(String text) {
        this.parentId = text;
    }

    public void setVerdict(String text) {
        this.verdict = text;
    }

    public void setAnnotation(String text) {
        this.annotation = text;
        if (!isEmpty(this.stepID)) {
            trl.addStepResults(null, stepID, verdict, annotation);
            lm.setLastMessage("Test Step Results added.");
        }
    }

    public void setStepVerdict(String text) {
        this.stepVerdict = text;
    }

    public void setStepId(String text) {
        // stepID=1667:verdict=Passed:annotation=good
        this.stepID = text;
    }

    /**
     * get Routines
     */
    public String getFieldValue() {
        return this.fieldValue;
    }

    public int getCount() {
        return this.count;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public String getCommand() {
        return this.command;
    }

    public void setEnvironment(String text) {
        if (!isEmpty(text)) {
            // System.getenv().put(text.split("=")[0], text.split("=")[1]);
            addEnv.put(text.split("=")[0], text.split("=")[1]);
        }
    }

    public int adminOperation(String module, String command) {
        Boolean isCreate = command.startsWith("create");
        Boolean isDelete = command.startsWith("delete");
        Boolean isEdit = command.startsWith("edit");

        try {
            Command cmd = new Command(module, command);
            if (isCreate || isEdit) {
                if (isEdit) {
                    cmd.addSelection(fl.findValueByName("name"));
                }

                for (IntegrityFieldValue fld : fl) {
                    if (fld.getValue().contentEquals("true")) {
                        cmd.addOption(new Option(fld.getName()));
                    } else if (fld.getValue().contentEquals("false")) {
                        cmd.addOption(new Option("no" + fld.getName()));
                    } else if (fld.getValue() != null && !fld.getValue().isEmpty()) {
                        cmd.addOption(new Option(fld.getName(), fld.getValue()));
                    }
                }
                for (IntegrityFieldValue fld : pl) {
                    if (fld.getValue() != null && !fld.getValue().isEmpty()) {
                        cmd.addOption(new Option(fld.getName(), fld.getValue()));
                    }
                }
                /*
                 if (command.contentEquals("createfield"))
                 cmd.addOption(new Option("type", pl.findValueByName("Type")));
                 if (pl.findValueByName("query") != null && !pl.findValueByName("query").isEmpty())
                 cmd.addOption(new Option("query", pl.findValueByName("query")));
                 */

            } else if (isDelete) {
                cmd.addSelection(fl.findValueByName("name"));
                cmd.addOption(new Option("Y"));
            }

            // cmd.addOption(new Option("param", sql));
            Response response = executeCmd(cmd);
            log(response.getCommandString());
            lm.setLastMessage(command + " ok.");
            return 0;
        } catch (APIException ex) {
            // Logger.getLogger(IntegrityFitNesse.class.getName()).log(Level.SEVERE, null, ex);
            ExceptionHandler eh = new ExceptionHandler(ex);
            log(eh.getMessage());
            lm.setLastMessage(eh.getMessage());
        }
        return -1;
    }

    /**
     *
     * @return
     */
    public int gatewayOperation() {
        String fileName = "";
        fileSize = "";
        OSCommandHandler osh = new OSCommandHandler(resultFilter);
        String cmd = "gateway export " + this.getLoginString() + " --silent ";
        for (IntegrityFieldValue current : fl) {
            cmd = cmd + " --" + current.getName() + "=\"" + current.getValue() + "\"";
        }
        for (IntegrityFieldValue current : pl) {
            cmd = cmd + " --" + current.getName() + "=\"" + current.getValue() + "\"";
            if (current.getName().contentEquals("file")) {
                fileName = current.getValue();
            }
        }
        cmd = cmd + " " + selection;
        log("Executing " + cmd);

        // clean before
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }

        int retCode = osh.executeCmd(cmd, true, addEnv);
        log("After gateway command, result is: " + retCode);
        // updateProgress(18, 20);

        if ((retCode == 0) && file.exists() && file.length() > 0) {
            lm.setLastMessage("Output file created with " + file.length() + " bytes.");
            fileSize = file.length() + "";
            return 0;
        }
        return retCode;
    }

    /**
     * Runs the im fields command.
     */
    public int execute() {

        Response response;
        String fullCommand;
        count = 0;
        Timer timer = new Timer();
        String tempFileName = "output.tmp";
        fieldList = "";

        // an 'invalid' module can be used to skip that row temporarely
        if (!isValidModule(module)) {
            lm.setLastMessage("Not a valid module '" + module + "', " + command + " skipped.");
            return 0;
        }

        log(timer.getStartTime(), "Command '" + module + " " + command + "' started.");
        // perform the correct action for each comamnd requested
        if (command.contentEquals("finditem")) {
            return findItem(pl.findValueByName(Config.fnType));
        } else if (command.contentEquals("createfield")
                || command.contentEquals("deletefield")
                || command.contentEquals("createtype")
                || command.contentEquals("deletetype")
                || command.contentEquals("createquery")
                || command.contentEquals("editfield")
                || command.contentEquals("deletequery")) {
            return adminOperation(module, command);
        } else if (command.contentEquals("findissue")) {
            return findItem(pl.findValueByName(Config.fnType));
        } else if (command.contentEquals("updateresult")) {
            return IntegritySQL.updateresult(this, pl);
        } else if (command.contentEquals("export")) {
            return gatewayOperation();
        } else {
            // Support for first release
            if (command.contentEquals("runquery")) {
                command = "issues";
            }

            // log("fileName: " + fileName);
            if (command.contentEquals("runchart")) {
                if (!fileName.isEmpty()) {
                    File file = new File(fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }

            // String 
            Command cmd = new Command(module, command);
            addLoginData(cmd);

            for (IntegrityFieldValue current : fl) {
                if (command.contentEquals("runtrigger")) {
                    addFieldValue(cmd, "invocationArgs=" + current.getName(), current.getValue());
                } else if (command.contentEquals("types")) {
                    addOption(cmd, "fields", current.getName());
                    fieldList = fieldList + (fieldList.isEmpty() ? "" : ",") + current.getValue();
                } else {
                    addFieldValue(cmd, "field=" + current.getName(), current.getValue());
                }
            }
            for (IntegrityFieldValue current : pl) {
                if (!isEmpty(current.getValue())) {
                    addOption(cmd, current.getName(), current.getValue());
                    // debug("Parameter --" + current.getName() + "=" + current.getValue() + " added.");
                }
            }

            // we also try to catch the output and count the number of rows
            addOption(cmd, "Y", "");
            if (command.contentEquals("deleteissue")) {
                addOption(cmd, "noconfirmRQ", "");
            }
            if (command.contentEquals("createcontent")) {
                addOption(cmd, "parentID", this.parentId);
            }
            if (command.contentEquals("runreport")) {
                addOption(cmd, "outputfile", tempFileName);
                addOption(cmd, "overwriteOutputFile", "");  // overwriteOutputFile
            }

            // Testing Part
            // if (!isEmpty(this.sessionId))
            //     addOption(cmd, "sessionId", this.sessionId);
            if (!isEmpty(this.verdict)) {
                addOption(cmd, "verdict", this.verdict);
                addOption(cmd, "annotation", this.annotation);
            }
            if (!isEmpty(this.stepVerdict)) {
                addOption(cmd, "stepVerdict", this.stepVerdict);
            }

            if (trl.size() > 0) {
                log("Test Step Results found ...");
                for (TestResult current : trl) {
                    addOption(cmd, "stepVerdict", current.getStepVerdict());
                    log("stepVerdict => " + current.getStepVerdict());
                }
            }

            // Handle Selection
            if (!isEmpty(selection)) {
                cmd.addSelection(selection);
                debug(" addSelection " + selection);
            }
            lm.init();

            try {
                // Track command       
                fullCommand = logCmd(cmd);
                log(timer.getTime(), fullCommand);

                //
                String query = pl.findValueByName("query");
                // log("query: " + query);

                // query is executed, count resultset
                if (!isEmpty(query) && !isEmpty(id) && command.contentEquals("issues")) { //  && isEmpty(fieldValue)) {
                    // im issues --user=veckardt --hostname=AVL --queryDefinition="((subquery[AVL All Releases]) and (field[ID]=41))"
                    cmd = new Command(Command.IM, "issues");
                    cmd.addOption(new Option("queryDefinition", "((subquery[" + query + "]) and (field[" + Config.fnID + "]=" + id + "))"));
                }

                // Execute command 
                response = executeCmd(cmd);

                lm.setLastMessage("Command '" + module + " " + command + "' ok");
                log(timer.getTime(), lm.getLastMessage());

                // if the operation has something to do with creation, then the returning id is printed
                if (!module.equals(Command.TM)) {
                    if (command.contains("create")) {
                        String itemId = response.getResult().getPrimaryValue().getId();
                        lm.setLastMessage("Item with ID '" + itemId + "' has been created");
                        log(timer.getTime(), lm.getLastMessage());
                        if (response.getExitCode() == 0) {
                            fieldValue = itemId; // return Integer.parseInt(itemId);      
                        }
                    }
                }

                if (command.contentEquals("types")) {
                    ResponseUtil.printResponse(response, 1, System.out);
                    WorkItem wi = response.getWorkItem(selection);
                    Boolean allFound = true;
                    for (String fieldName : fieldList.split(",")) {
                        Boolean found = false;
                        Field field = wi.getField("visibleFields");
                        ListIterator li = field.getList().listIterator();
                        while (li.hasNext()) {
                            Item item = (Item) li.next();
                            if (item.getId().contentEquals(fieldName)) {
                                found = true;
                            }
                        }
                        if (!found) {
                            allFound = false;
                        }
                    }
                    if (allFound) {
                        lm.setLastMessage(fieldList + " is part of visibleFields");
                        return 0;
                    } else {
                        lm.setLastMessage("Not all fields could be found in visibleFields!");
                        return -1;
                    }
                }

                // Special handling for view issue to return one value only
                if (command.contentEquals("viewissue")) {
                    //
                    //
                    // remove: WorkItem wi = response.getWorkItem(selection);
                    //
                    //

                    // START: Support for versioned Items:
                    WorkItem wi = null;
                    WorkItemIterator wit = response.getWorkItems();
                    while (wit.hasNext()) {
                        wi = wit.next();
                        if (wi.contains("Live Item ID") && wi.contains("Revision")) {
                            // check if it the same
                            if ((wi.getField("Live Item ID").getInteger() + "-" + wi.getField("Revision").getString()).contentEquals(selection)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    // END: Support for versioned Items.

                    if (isEmpty(fieldName)) {
                        fieldName = Config.fnID;
                    }

                    // NEW CODE: 2015- 06- 04
                    //
                    // To handle the correct values of the relationships in case of versioned items
                    //
                    String fieldType = "";
                    if (fieldTypes.containsKey(fieldName)) {
                        fieldType = fieldTypes.get(fieldName);
                    } else {
                        Command cmdFields = new Command(Command.IM, "fields");
                        cmdFields.addOption(new Option("fields", "type"));
                        cmdFields.addSelection(fieldName);
                        Response fieldResponse = executeCmd(cmdFields);
                        WorkItem fieldWorkItem = fieldResponse.getWorkItem(fieldName);
                        fieldType = fieldWorkItem.getField("type").getString();
                    }
                    log("fieldWorkItem: " + fieldType);

                    // 
                    List<String> items = Arrays.asList(fieldName.split(","));
                    fieldValue = "";
                    // for all fieldNames 
                    for (int i = 0; i < items.size(); i++) {
                        if (i > 0) {
                            fieldValue = fieldValue + ",";
                        }
                        // log ("fieldType: "+fieldType);
                        // if relationship, display the correct item ID incl. version information 
                        if (fieldType.contentEquals("relationship")) {
                            String relValues = wi.getField(items.get(i).toString()).getValueAsString();
                            if (!relValues.isEmpty()) {
                                for (String relValue : relValues.split(",")) {
                                    Command cmdIssue = new Command(Command.IM, "issues");
                                    cmdIssue.addOption(new Option("fields", "Live Item ID,Major Version ID,Minor Version ID"));
                                    cmdIssue.addOption(new Option("includeVersionedItems"));
                                    // log("getting issue relValue: " + relValue);
                                    cmdIssue.addSelection(relValue);
                                    Response issueResponse = executeCmd(cmdIssue);
                                    WorkItem issueWI = issueResponse.getWorkItem(relValue);
                                    if (!issueWI.getField("Live Item ID").getValueAsString().contentEquals(relValue)) {
                                        fieldValue = fieldValue + (fieldValue.isEmpty() ? "" : ",") + issueWI.getField("Live Item ID").getValueAsString() + "-" + issueWI.getField("Major Version ID").getValueAsString() + "." + issueWI.getField("Minor Version ID").getValueAsString();
                                    } else {
                                        fieldValue = fieldValue + (fieldValue.isEmpty() ? "" : ",") + relValue;
                                    }
                                }
                            }
                        } else {
                            fieldValue = fieldValue + "" + wi.getField(items.get(i).toString()).getValueAsString();
                        }
                    }
                    // END of NEW CODE: 2015- 06- 04
                }

                query = pl.findValueByName("query");
                // log("query: " + query);

                // query is executed, count resultset
                if (!isEmpty(query) && command.contentEquals("issues")) { //  && isEmpty(fieldValue)) {
                    // loop through all results
//                    if (!isEmpty(id)) {
//                        // im issues --user=veckardt --hostname=AVL --queryDefinition="((subquery[AVL All Releases]) and (field[ID]=41))"
//                        cmd = new Command(Command.IM, "issues");
//                        cmd.addOption(new Option("queryDefinition", "((subquery[" + query + "]) and (field[" + Config.fnID + "]=" + id + "))"));
//                        response = executeCmd(cmd);
//                    }
                    WorkItemIterator wii = response.getWorkItems();
                    while (wii.hasNext()) {
                        wii.next();
                        count++;
                    }
                    fieldValue = Integer.toString(count);
                    lm.setLastMessage("Query has returned " + count + " item(s).");
                }

                // we also try to catch the output and count the number of rows
                if (command.contentEquals("runreport")) {
                    FileHandler fh = new FileHandler(tempFileName);
                    count = fh.getLineCount();
                }

                // release the test step results array for further use in same table
                if (command.contentEquals("createresult")) {
                    trl.clear();
                }

                if (command.contentEquals("runchart") && !fileName.isEmpty()) {
                    fileSize = (new File(fileName)).length() + "";
                    fileName = "";
                }

                try {
                    return response.getExitCode();
                } catch (APIInternalError ex) {
                    if (command.contentEquals("extractwordtemplates")) {
                        ResponseUtil.printResponse(response, 1, System.out);
                        return 0;
                    } else {
                        throw ex;
                    }
                }
            } catch (APIException ae) {
                log("*****************************************************************");
                log("Error running command '" + command + "': ");
                ExceptionHandler eh = new ExceptionHandler(ae);
                log(eh.getMessage());
                lm.setLastMessage(eh.getMessage());
                log("*****************************************************************");
                return -1;
            }
        }
    }

    /**
     * Runs the find Issue command for the given information requested
     *
     * @return the issue ID
     */
    public String findItem() {
        // look up the mandatory type value
        String type = pl.findValueByName(Config.fnType);
        int res = findItem(type);
        // clear the search parameters
        pl.clear();
        fl.clear();
        // return the result
        return fieldValue;
    }

    /**
     * Simple routine to find an issue by either type, summary, ID, and/or state
     *
     * @param type Issue Type
     * @return IF successfully 0, otherwise -1
     */
    public int findItem(String type) {

        // im issues --querydefinition="((field[Type]=Project)and (field[ID]=101) and (field[Summary]contains'Project for Release 1')and (field[State]="Defined"))"
        // im issues --querydefinition="((field[Type]=Project)and (field[Summary]contains'Project for Release 1')and (field[State]="Defined"))"
        Command cmd;
        String fullCommand = "";
        Response response;
        WorkItem wi;
        String querydefinition = "";
        String fieldList = Config.fnID + "," + Config.fnState;
        Timer timer = new Timer();
        fieldValue = "";
        Boolean ok = false;

        try {

            // im types --fields=ID,name --fieldsdelim=,
            cmd = new Command("im", "issues");
            if (!isEmpty(type)) {
                querydefinition = "(field[" + Config.fnType + "]=\"" + type + "\")";
            }

            for (IntegrityFieldValue current : fl) {
                if (!isEmpty(current.getValue())) {
                    String operand = "=";
                    // log("current.getName() > "+current.getName());
                    if (current.getName().equals(Config.fnID)) {
                        // List<String> aid = Arrays.asList(current.getValue().split("\\s*,\\s*"));
                        // log ("aid.size() > "+aid.size());
                        if (StringUtils.countMatches(current.getValue(), ",") > 1) {
                            String subquery = QueryUtils.getSubQuery(current.getValue());
                            querydefinition = QueryUtils.addIfNull(querydefinition, "and", "(" + subquery + ")");
                        } else {
                            querydefinition = QueryUtils.addIfNull(querydefinition, "and", "(field[" + current.getName() + "]" + operand + " \"" + current.getValue() + "\")");
                        }
                    } else {
                        if ((current.getName().equals(Config.fnSummary)) || (current.getName().equals(Config.fnText))) {
                            operand = "contains";
                        }
                        querydefinition = QueryUtils.addIfNull(querydefinition, "and", "(field[" + current.getName() + "]" + operand + " \"" + current.getValue() + "\")");
                    }
                    fieldList = fieldList + "," + current.getName();
                }
            }

            // construct the command
            cmd.addOption(new Option("fields", fieldList));
            cmd.addOption(new Option("querydefinition", "(" + querydefinition + ")"));
            fullCommand = logCmd(cmd);
            log(timer.getTime(), fullCommand);

            // Execute the command
            response = executeCmd(cmd);

            // loop through all results to find the correct item
            WorkItemIterator wii = response.getWorkItems();
            while (wii.hasNext()) {
                wi = wii.next();

                String summary = fl.findValueByName(Config.fnSummary);
                String text = fl.findValueByName(Config.fnText);
                if (!isEmpty(summary)) {
                    String wisum = wi.getField(Config.fnSummary).getValueAsString();
                    log(wisum + " found, expected was '" + summary + "'!");
                    if (wisum.matches(summary)) {
                        fieldValue = wi.getField(Config.fnID).getValueAsString();
                        lm.setLastMessage("Item " + fieldValue + " found.");
                        return 0;
                    }
                } else {
                    if (!isEmpty(text)) {
                        String witxt = wi.getField(Config.fnText).getValueAsString();
                        log(witxt + " found, expected was '" + text + "'!");
                        if (witxt.matches(text)) {
                            fieldValue = wi.getField(Config.fnID).getValueAsString();
                            lm.setLastMessage("Item " + fieldValue + " found.");
                            return 0;
                        }
                    } else {
                        // Handle multiple ID's, such as 1234,3425,3322
                        fieldValue = QueryUtils.addIfNull(fieldValue, ",", wi.getField(Config.fnID).getValueAsString());
                        lm.setLastMessage("Item(s) " + fieldValue + " found.");
                        ok = true;
                    }
                }
            } // while           
            lm.setLastMessage("Item not found");
            return (ok ? 0 : -1);
        } catch (APIException ex) {
            log("Error running command '" + fullCommand + "': ");
            ExceptionHandler eh = new ExceptionHandler(ex);
            log(eh.getMessage());
            lm.setLastMessage(eh.getMessage());
            return -1;
        }
    }

    /**
     * Updates the result information by linking a defect to it Currently it is
     * the hard core way, but it works like that Can not find any java class
     * which does that (except the trigger bean)
     *
     * @return if successfully 0, otherwise -1
     */
    public int updateresult() {

        String SessionID = pl.findValueByName("SessionId");
        String DefectID = pl.findValueByName("DefectId");
        String CaseID = pl.findValueByName("CaseId");
        String sql;
        Response response;
        Command cmd;

        if (isEmpty(SessionID)) {
            throw new IllegalArgumentException("Parameter 'SessionID' can't be empty!");
        }
        if (isEmpty(DefectID)) {
            throw new IllegalArgumentException("Parameter 'DefectID' can't be empty!");
        }
        if (isEmpty(CaseID)) {
            throw new IllegalArgumentException("Parameter 'CaseID' can't be empty!");
        }

        try {
            cmd = new Command("im", "diag");
            cmd.addOption(new Option("diag", "runsql")); // im diag --diag=runsql --param=
            // Step 1: Find the Result ID
            // Step 2: Insert the result ID
            sql = "insert into TRIMap select max(ID), " + DefectID + " from TRResults where SessionID=" + SessionID + " and CaseID=" + CaseID;
            cmd.addOption(new Option("param", sql));
            response = executeCmd(cmd);
            log(response.getCommandString());
            lm.setLastMessage("Defect #" + DefectID + " assigned to Test Case #" + CaseID);
            return 0;
        } catch (APIException ex) {
            // Logger.getLogger(IntegrityFitNesse.class.getName()).log(Level.SEVERE, null, ex);
            ExceptionHandler eh = new ExceptionHandler(ex);
            log(eh.getMessage());
            lm.setLastMessage(eh.getMessage());
        }
        return -1;
    }
}
