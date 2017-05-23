/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import com.mks.api.CmdRunner;
import com.mks.api.Command;
import com.mks.api.IntegrationPoint;
import com.mks.api.IntegrationPointFactory;
import com.mks.api.Option;
import com.mks.api.OptionList;
import com.mks.api.SelectionList;
import com.mks.api.Session;
import com.mks.api.response.APIException;
import com.mks.api.response.ApplicationException;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;
import com.mks.api.util.MKSLogger;
import com.mks.api.util.ResponseUtil;
import com.ptc.services.common.api.IntegrityConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Basic Integrity API Class
 *
 * @author veckardt
 */
public class IntegrityAPI {

    private String defaultUsername;
    private String defaultPassword;
    private int defaultPort;
    private String defaultHostname;
    private CmdRunner cmdRunner;
    private Session apiSession;
    private boolean debug = true;
    // private static final String VERSION =
    //         IntegrationPointFactory.getAPIVersion().substring(0, IntegrationPointFactory.getAPIVersion().indexOf(' '));
    private static final int MAJOR_VERSION = 4; // Integer.parseInt(VERSION.substring(0, VERSION.indexOf('.')));
    private static final int MINOR_VERSION = 11; // Integer.parseInt(VERSION.substring(VERSION.indexOf('.') + 1, VERSION.length()));
    // User's current working directory
    private static final String tmpDir = System.getProperty("java.io.tmpdir");
    // System file separator
    private static final String fs = System.getProperty("file.separator");
    // Log file location
    public static final String LOGFILE = tmpDir + fs + "IntegrityFit_" + getDate() + ".log";
    // Class variables used to create an API Session
    private static MKSLogger logger = new MKSLogger(LOGFILE);
    Properties loggerProps;

    /**
     * Returns true, if the module name is valid, otherwise false
     *
     * @param module Integrity module short name
     * @return true if valid module, otherwise false
     */
    public Boolean isValidModule(String module) {
        if (!module.equals(Command.IM) && !module.equals("gateway") && !module.equals(Command.SI) && !module.equals(Command.AA) && !module.equals(Command.TM)) {
            log("*****************************************************************");
            log("Module '" + module + "' is invalid, must be " + Command.IM + " or " + Command.SI + " or " + Command.AA + " or " + Command.TM);
            log("*****************************************************************");
            return false;
        }
        return true;
    }

    /**
     * Is empty checker for a string
     *
     * @param s the string to check
     * @return true if empty, otherwise false
     */
    public boolean isEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    public static void log(String text) {
        System.out.println(text);
        logger.message(MKSLogger.DEBUG, MKSLogger.LOW, text);
    }

    public static void print(String text) {
        System.out.print(text);
        logger.message(MKSLogger.DEBUG, MKSLogger.LOW, text);
    }

    public static void log(String text1, String text2) {
        System.out.println(text1 + ": " + text2);
        logger.message(MKSLogger.DEBUG, MKSLogger.LOW, text1 + ": " + text2);
    }

    public String getDefaultHostname() {
        return this.defaultHostname;
    }

    public String getDefaultUsername() {
        return this.defaultUsername;
    }

    public String getDefaultPassword() {
        return this.defaultPassword;
    }

    public String getDefaultPort() {
        return Integer.toString(this.defaultPort);
    }

    public String getLoginString() {
        return " --hostname=" + defaultHostname + " --port=" + defaultPort + " --user=" + defaultUsername;
    }

    private void setDefaultHostname(String hostname) {
        if (!isEmpty(hostname)) {
            this.defaultHostname = hostname;
            cmdRunner.setDefaultHostname(hostname);
        }
    }

    private void setDefaultPort(int defaultPort) {
        if ((defaultPort != 0)) {
            this.defaultPort = defaultPort;
            cmdRunner.setDefaultPort(defaultPort);
        }
    }

    private void setDefaultUsername(String username) {
        if (!isEmpty(username)) {
            this.defaultUsername = username;
            cmdRunner.setDefaultUsername(username);
        }
    }

    private void setDefaultPassword(String defaultPassword) {
        if (!isEmpty(defaultPassword)) {
            this.defaultPassword = defaultPassword;
            cmdRunner.setDefaultPassword(defaultPassword);
        }
    }

    /**
     * Perform full connect activity via si connect
     *
     * @param hostname the hostname
     * @param port the port
     * @param username the suername
     * @param password the password
     */
    public void connect(String hostname, int port, String username, String password) {
        // logger = new MKSLogger(LOGFILE);
        logger.configure(getLoggerProperties());
        logger.message("Logger initialized (1) ...");
        IntegrationPointFactory ipf = IntegrationPointFactory.getInstance();
        try {
            IntegrationPoint ip = ipf.createLocalIntegrationPoint(MAJOR_VERSION, MINOR_VERSION);
            // get a local command runner
            ip.setAutoStartIntegrityClient(true);
            apiSession = ip.createSession();
            cmdRunner = apiSession.createCmdRunner();

            //Setup connection if not null
            setDefaultUsername(username);
            setDefaultPassword(password);
            setDefaultHostname(hostname);
            setDefaultPort(port);

            // cmdRunner.setDefaultImpersonationUser(username);
            //	Test the connection is valid
            cmdRunner.execute(new String[]{Command.IM, "connect"});

        } catch (APIException apiEx) {
            logException(apiEx);
        }
    }

    /**
     * Uses the command getCommonSession to create a connection with Integrity
     * client
     *
     * @param hostname the hostname
     * @param port the port
     * @param username the username
     * @param password the password
     */
    public void connectCommonSession(String hostname, int port, String username, String password) {
        logger.configure(getLoggerProperties());
        logger.message("Logger initialized (2) ...");

        IntegrationPointFactory ipf = IntegrationPointFactory.getInstance();
        try {
            // get a local command runner
            IntegrationPoint ip = ipf.createLocalIntegrationPoint(MAJOR_VERSION, MINOR_VERSION);
            ip.setAutoStartIntegrityClient(true);
            apiSession = ip.getCommonSession();
            cmdRunner = apiSession.createCmdRunner();

            //Setup connection if not null
            setDefaultUsername(username);
            setDefaultPassword(password);
            setDefaultHostname(hostname);
            setDefaultPort(port);

            //	Test the connection is valid
            cmdRunner.execute(new String[]{Command.IM, "connect"});

        } catch (APIException apiEx) {
            logException(apiEx);
        }
    }

    /**
     * Simple version without parameters
     */
    public void connectCommonSession() {
        connectCommonSession("", 0, "", "");
    }

    public IntegrityAPI() {

    }

    public IntegrityAPI(Map<String, String> env, String logFileName) {
        if (env == null) {
            return;
        }
        logFileName = tmpDir + fs + logFileName + "_" + getDate() + ".log";
        logger = new MKSLogger(logFileName);
        logger.configure(getLoggerProperties());
        logger.message("Logger initialized...");
        IntegrityConnection ic;

        // Create a Local Integration Point
        if (env.get("MKSSI_HOST") == null) {
            ic = new IntegrityConnection("integrityserver", 7001, "veckardt");
            logger.message(MKSLogger.DEBUG, MKSLogger.LOW, "Loggin in with default user " + ic.toString());
        } else {
            ic = new IntegrityConnection(env.get("MKSSI_HOST"), Integer.parseInt(env.get("MKSSI_PORT")), env.get("MKSSI_USER"));
            logger.message(MKSLogger.DEBUG, MKSLogger.LOW, "Loggin in with session user " + ic.toString());
        }

        try {
            logger.message(MKSLogger.DEBUG, MKSLogger.LOW, "Creating local Integration Point ...");
            // integrationPoint = IntegrationPointFactory.getInstance().createIntegrationPoint(host, port, MAJOR_VERSION, MINOR_VERSION); // 
            IntegrationPoint integrationPoint = IntegrationPointFactory.getInstance().createLocalIntegrationPoint(MAJOR_VERSION, MINOR_VERSION);
            apiSession = integrationPoint.getCommonSession();
            // apiSession.setDefaultPassword("password");
            logger.message(MKSLogger.DEBUG, MKSLogger.LOW, "Testing Connection ...");
            cmdRunner = apiSession.createCmdRunner();

            setDefaultUsername(ic.getUser());
            setDefaultHostname(ic.getHost());
            setDefaultPort(ic.getPort());

            cmdRunner.execute(new String[]{Command.IM, "connect"});
            // cmdRunner.release();

            // Integrity connected ...
            // initatedByIntegrity = true;
            logger.message(MKSLogger.GENERAL, MKSLogger.LOW, "Connected successfully.");
        } catch (APIException ex) {
            logger.message(MKSLogger.ERROR, MKSLogger.LOW, ex.getLocalizedMessage());
            logger.exception(ex);
            // Logger.getLogger(IntegrityAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            logger.message(MKSLogger.ERROR, MKSLogger.LOW, ex.toString());
            logger.exception(ex);
        }
    }

    /**
     * Runs the im disconnect command.
     */
    public void disconnect() {
        // Command to disconnect
        Command cmd = new Command(Command.IM, "disconnect");
        // Execute disconnect
        try {
            Response response = cmdRunner.execute(cmd);
            cmdRunner.release();
            // ResponseUtil.printResponse(response,1,System.out);
        } catch (ApplicationException ae) {
            log("Error running disconnect command: ");
            logException(ae);
        } catch (APIException ex) {
            log("Error running disconnect command: ");
            logException(ex);
        }
    }

    /**
     * Executes the command constructed
     *
     * @param cmd the cmd handler
     * @return the response object
     * @throws APIException
     */
    // public Response execute(Command cmd) throws APIException {
    //     return cmdRunner.execute(cmd);
    // }
    public void addLoginData(Command cmd) {
        // add options
        // if (cmdRunner.getDefaultImpersonationUser() == null)
        if (!isEmpty(this.getDefaultUsername())) {
            addOption(cmd, "user", this.getDefaultUsername());
        }
        if (!isEmpty(this.getDefaultPassword())) {
            addOption(cmd, "password", this.getDefaultPassword());
        }
        addOption(cmd, "hostname", this.getDefaultHostname());
        addOption(cmd, "port", this.getDefaultPort() + "");
    }

    /**
     * Get cmdRunner
     *
     * @return the current cmdRunner
     */
    // private CmdRunner getCmdRunner () {
    //     return cmdRunner;
    // }    
    /**
     * Runs the im disconnect command.
     */
    public void releaseCmdRunner() {
        // Execute disconnect
        try {
            cmdRunner.release();
            // ResponseUtil.printResponse(response,1,System.out);
        } catch (ApplicationException ae) {
            log("Error running disconnect releaseCmdRunner: ");
            logException(ae);
        } catch (APIException ex) {
            log("Error running disconnect releaseCmdRunner: ");
            logException(ex);
        }
    }

    /**
     * Returns a type workitem from the current type
     *
     * @param type An Integrity type
     * @return the workitem from the response
     */
    public WorkItem getTypeWI(String type) {
        Command cmd = new Command(Command.IM, "viewtype");
        cmd.addSelection(type);
        try {
            Response response = executeCmd(cmd); // cmdRunner.execute(cmd);
            // ResponseUtil.printResponse(response,1,System.out);
            return response.getWorkItem(type);
        } catch (APIException ex) {
            logException(ex);
        }
        return null;
    }

    /**
     * Returns the item type from the current item id
     *
     * @param itemId An Item Id
     * @return The accociated type name
     */
    public String getItemType(String itemId) {
        // VE: connectCommonSession();
        Command cmd = new Command(Command.IM, "viewissue");
        cmd.addSelection(itemId);
        try {
            Response response = executeCmd(cmd); // VE: cmdRunner.execute(cmd);
            // ResponseUtil.printResponse(response,1,System.out);
            WorkItem wi = response.getWorkItem(itemId);
            // releaseCmdRunner();
            return wi.getField("Type").getValueAsString();

        } catch (APIException ex) {
            logException(ex);
        }
        // releaseCmdRunner();
        return null;
    }

    /**
     * Adds a single option to the command
     *
     * @param cmd the command object
     * @param option the option
     * @param optionValue the value for this option (optional)
     */
    public void addOption(Command cmd, String option, String optionValue) {
        if (!isEmpty(option)) {
            if (isEmpty(optionValue)) {
                cmd.addOption(new Option(option));
                // VE: debug(" addOption " + option);
            } else {
                cmd.addOption(new Option(option, optionValue.replace("(null)", "")));
                // VE: debug(" addOption " + option + "=" + optionValue);
            }
        }
    }

    /**
     * Only add through addOption if value not empty
     *
     * @param cmd the command object
     * @param option the option
     * @param optionValue the value for the option
     */
    public void addFieldValue(Command cmd, String option, String optionValue) {
        if (!isEmpty(option)) {
            if (!isEmpty(optionValue)) {
                cmd.addOption(new Option(option, optionValue.replace("(null)", "")));
                // VE: debug(" addOption " + option + "=" + optionValue);
            }
        }
    }

    /**
     * Log additional APIException information. Only done since this is a
     * sample.
     *
     * @param ae APIException to log
     */
    public void logException(APIException ae) {
        log(" message:     " + ae.getMessage()
                + "\n class:       " + ae.getClass().getName()
                + "\n exceptionId: " + ae.getExceptionId());
    }

    /**
     * sets the global debug mode on
     */
    public void debugOn() {
        debug = true;
    }

    /**
     * prints a text if debug mode is enabled
     *
     * @param text
     */
    public void debug(String text) {
        if (debug) {
            log(text);
        }
    }

    /**
     * Prepares the command to be put into the log, ready to get executed also
     * from command line
     *
     * @param cmd the integrity command
     */
    public String logCmd(Command cmd) {
        String fullCmd = cmd.getApp() + " " + cmd.getCommandName();

        // Handle the options
        OptionList optionList = cmd.getOptionList();
        for (Iterator oi = optionList.getOptions(); oi.hasNext();) {
            Option option = (Option) oi.next();

            String ostrg = option.toString();
            int dp = ostrg.indexOf("=");
            if ((dp > 0) && (option.getValue().indexOf(" ") > 0)) {
                ostrg = ostrg.substring(0, dp + 1) + "'" + ostrg.substring(dp + 1) + "'";
            }
            fullCmd = fullCmd + " " + ostrg;
        }

        // Handle the Selection
        SelectionList sl = cmd.getSelectionList();
        for (int i = 0; i < sl.size(); i++) {
            String selection = sl.getSelection(i);
            fullCmd = fullCmd + " " + selection;
        }

        return fullCmd;
    }

    private Properties getLoggerProperties() {
        // Initialize logger properties
        loggerProps = new Properties();

        // Logging Categories
        loggerProps.put("mksis.logger.message.includeCategory.DEBUG", "10");
        loggerProps.put("mksis.logger.message.includeCategory.WARNING", "10");
        loggerProps.put("mksis.logger.message.includeCategory.GENERAL", "10");
        loggerProps.put("mksis.logger.message.includeCategory.ERROR", "10");
        // Output Format
        loggerProps.put("mksis.logger.message.defaultFormat", "{2}({3}): {4}");
        loggerProps.put("mksis.logger.message.format.DEBUG", "{2}({3}): {4}");
        loggerProps.put("mksis.logger.message.format.WARNING", "* * * * {2} * * * * ({3}): {4}");
        loggerProps.put("mksis.logger.message.format.ERROR", "* * * * {2} * * * * ({3}): {4}");

        return loggerProps;
    }

    public MKSLogger getLogger() {
        return logger;
    }

    private final static String getDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("CET"));
        return df.format(new Date());
    }

    public Response executeCmd(Command cmd)
            throws APIException {

        StringBuffer cmdDebug;
        long timestamp;
        // try {

        CmdRunner cr = null;
        String cmdArgs[] = cmd.toStringArray();
        cmdDebug = new StringBuffer();
        cmdDebug.append("IntegrityAPI execute [ ");
        for (int i = 0; i < cmdArgs.length; i++) {
            cmdDebug.append(cmdArgs[i]).append(' ');
        }

        cmdDebug.append("] ");
        timestamp = System.currentTimeMillis();
        Response response1;
        try {
            cr = apiSession.createCmdRunner();
            response1 = cr.execute(cmd);
        } catch (APIException ex) {
            Response response = ex.getResponse();
            if (response != null) {
                WorkItemIterator wii = response.getWorkItems();
                if (wii != null && response.getWorkItemListSize() == 1) {
                    try {
                        wii.next();
                    } catch (APIException wex) {
                        ex = wex;
                    }
                }
            }
            cmdDebug.append(": ").append(ex.getExceptionId()).append(": ").append(ex.getMessage()).append(" ");
            throw ex;
        }
        timestamp = System.currentTimeMillis() - timestamp;
        cmdDebug.append("[").append(timestamp).append("ms]");
        logger.message("DEBUG", 5, cmdDebug.toString());
        try {
            if (cr != null) {
                cr.release();
            }
        } catch (APIException ex) {
        }
        return response1;
        // } catch (Exception ex) {
        //     Exception exception;
        //     timestamp = System.currentTimeMillis() - timestamp;
        //     cmdDebug.append("[").append(timestamp).append("ms]");
        //     Logger.message("DEBUG", 5, cmdDebug.toString());
        //     try {
        //         if (cr != null) {
        //             cr.release();
        //         }
        //     } catch (APIException ex) {
        //     }
        //     throw exception;
        // 
        // }
        // Exception exception;
        // exception;
    }
}
