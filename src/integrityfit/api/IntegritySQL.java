/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.Response;
import com.mks.api.util.ResponseUtil;
import integrityfit.Config;
import integrityfit.IntegrityFitNesse;
import integrityfit.IntegrityTransactions;
import integrityfit.utils.ExceptionHandler;
import integrityfit.utils.FieldValueListArray;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Very special SQL Commands, executed by runSQL
 *
 * @author veckardt
 */
public class IntegritySQL {
    // select distinct [ParentID] from [IntegrityTraining].[dbo].[IssueDeltas] where CreatedDate >= '2013-12-02 12:58:13.887' and ModifiedUser is not null

    // IntegrityTransactions it;
    // public IntegritySQL (IntegrityTransactions it) {
    //     this.it = it;
    // }
    public static List<String> getItemsInTimeFrame(IntegrityTransactions it, Date startDate, Date endDate) {
        // List<String> itemIDs = new ArrayList<>();
        String[] s;
        List<String> ls = new ArrayList<String>();
        Response response;
        Command cmd;
        String sql;

        String startStrg = new SimpleDateFormat(Config.dtDayTimeFormatSQL).format(startDate) + ".000";
        String endStrg = new SimpleDateFormat(Config.dtDayTimeFormatSQL).format(endDate) + ".999";

        try {
            cmd = new Command(Command.IM, "diag");
            cmd.addOption(new Option("diag", "runsql")); // im diag --diag=runsql --param=
            // Retrieve all Issue Ids which have been modified within a certain timeframe
            sql = "select distinct ParentID from IssueDeltas "
                    + " where CreatedDate >= '" + startStrg + "' "
                    + " and CreatedDate <= '" + endStrg + "' "
                    + " and ModifiedUser is not null";
            cmd.addOption(new Option("param", sql));
            // ia.log(sql);
            response = it.executeCmd(cmd);
            // ResponseUtil.printResponse(response,1,System.out);
            // ia.log(response.getResult().getMessage());
            // it.log(response.getCommandString());
            s = response.getResult().getMessage().split("\\n");
            for (int i = 0; i < s.length; i++) {
                if (s[i].trim().matches("\\d+")) {
                    ls.add(s[i].trim());
                }
            }

        } catch (APIException ex) {
            // Logger.getLogger(IntegrityFitNesse.class.getName()).log(Level.SEVERE, null, ex);
            ExceptionHandler eh = new ExceptionHandler(ex);
            it.log(eh.getMessage());
        }
        return ls;
    }

    /**
     * Updates the result information by linking a defect to it Currently it is
     * the hard core way, but it works like that Can not find any java class
     * which does that (except the trigger bean)
     *
     * @return if successfully 0, otherwise -1
     */
    public static int updateresult(
            IntegrityFitNesse ia,
            FieldValueListArray pl) {
        String SessionID = pl.findValueByName("SessionId");
        String DefectID = pl.findValueByName("DefectId");
        String CaseID = pl.findValueByName("CaseId");
        String sql;
        Response response;
        Command cmd;

        if (ia.isEmpty(SessionID)) {
            throw new IllegalArgumentException("Parameter 'SessionID' can't be empty!");
        }
        if (ia.isEmpty(DefectID)) {
            throw new IllegalArgumentException("Parameter 'DefectID' can't be empty!");
        }
        if (ia.isEmpty(CaseID)) {
            throw new IllegalArgumentException("Parameter 'CaseID' can't be empty!");
        }

        try {
            cmd = new Command(Command.IM, "diag");
            cmd.addOption(new Option("diag", "runsql")); // im diag --diag=runsql --param=
            // Step 1: Find the Result ID
            // Step 2: Insert the result ID
            sql = "insert into TRIMap select max(ID), " + DefectID + " from TRResults where SessionID=" + SessionID + " and CaseID=" + CaseID;
            cmd.addOption(new Option("param", sql));
            response = ia.executeCmd(cmd);
            ia.log(response.getCommandString());
            ia.setLastMessage("Defect #" + DefectID + " assigned to Test Case #" + CaseID);
            return 0;
        } catch (APIException ex) {
            // Logger.getLogger(IntegrityFitNesse.class.getName()).log(Level.SEVERE, null, ex);
            ExceptionHandler eh = new ExceptionHandler(ex);
            ia.log(eh.getMessage());
            ia.setLastMessage(eh.getMessage());
        }
        return -1;
    }
}
