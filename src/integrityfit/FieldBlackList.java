/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit;

import com.mks.api.response.Field;

/**
 * Provides a method if a field shall be extracted or not Used by STeF Retriever
 *
 * @author veckardt
 */
public class FieldBlackList {

    /**
     * Returns true if valid field, otherwise false
     *
     * @param type The Integrity type to check
     * @param docType The doc type, such as segment, node, none
     * @param fl the individual field to check
     * @return true if valid field, otherwise false
     */
    public static boolean validField(String type, String docType, Field fl) {
        // debug("checking "+fl.getName());
        if ((fl.getName().contentEquals("MKSIssueAddedRelationships"))
                || fl.getName().contentEquals("MKSIssueRemovedRelationships")
                || fl.getName().contentEquals("MKSIssueModifiedBy")
                || fl.getName().contentEquals("MKSIssueModifiedDate")
                || fl.getName().contentEquals("Closed Change Order Count")
                || fl.getName().contentEquals("Content Back Trace Count")
                || fl.getName().contentEquals("Content Fail Count")
                || fl.getName().contentEquals("Content Other Count")
                || fl.getName().contentEquals("Content Pass Count")
                || fl.getName().contentEquals("Content Run Count")
                || fl.getName().contentEquals("Content Without Back Traces Count")
                || fl.getName().contentEquals("Content Without Back Traces Percentage")
                || fl.getName().contentEquals("Included Document Count")
                || fl.getName().contentEquals("Inserted Document Count")
                || fl.getName().contentEquals("Meaningful Content Count")
                || fl.getName().contentEquals("Open Change Order Count")
                || fl.getName().contentEquals("Outstanding Change Request Count")
                || fl.getName().contentEquals("Suspect Content Count")
                || fl.getName().contentEquals("Suspect Content Percentage")
                || fl.getName().contentEquals("Suspect Relationship Count")
                || fl.getName().contentEquals("Total Content Fail Count")
                || fl.getName().contentEquals("Total Content Other Count")
                || fl.getName().contentEquals("Total Content Pass Count")
                || fl.getName().contentEquals("Total Content Run Count")
                || fl.getName().contentEquals("ANT_testOperation")
                || fl.getName().endsWith("Tests As Of Date Set")
                || fl.getName().endsWith("Tests As Of Date")
                || fl.getName().endsWith("Input Revision Date")//
                || fl.getName().endsWith("Document Churn from Initial Baseline")
                || fl.getName().endsWith("Root ID")
                || fl.getName().endsWith("Yesterday Document Churn")
                || fl.getName().endsWith("Last Week Document Churn")
                || fl.getName().endsWith("Modified Count Since Initial Baseline")
                || fl.getName().endsWith("Needs New Changes")
                || fl.getName().endsWith("Root Document")
                || fl.getName().endsWith("Documented By")
                || fl.getName().endsWith("Include Subsegment in Metrics")
                || fl.getName().endsWith("IncludeReference")
                || fl.getName().endsWith("Trace Status")
                || fl.getName().endsWith("Changes Authorized")
                // 
                || fl.getName().endsWith("Live Item ID")
                
                || fl.getName().endsWith("Authorized Change Count")
                || fl.getName().endsWith("Contains")) {
            return false;
        }

        // System.out.println("==>> " + fl.getItem().getModelType());

        // String docType = tl.getDocType(type);
        if (docType.contentEquals("segment")) {
            if ((fl.getName().endsWith("Summary"))
                    || fl.getName().contentEquals("Referenced By")
                    || fl.getName().contentEquals("All Content Count")) {
                return false;
            }
        } else if (docType.contentEquals("node")) {
            if (fl.getName().endsWith("Reference Mode")
                    || fl.getName().endsWith("References") // || fl.getName().endsWith("Contained By")    // 
                    ) // 
            {
                return false;
            }
        }
        return true;
    }
}
