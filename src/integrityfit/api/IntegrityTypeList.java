/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import com.mks.api.response.WorkItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple List for the integrity types Contains only two columns: the type name
 * and the column 'documentClass'
 *
 * @author veckardt
 */
public class IntegrityTypeList extends HashMap<String, String> {

    // Map<String, String> typeList = new HashMap<>();
    /**
     * Returns the Doc Class for the type
     *
     * @param type An Integrity type
     * @return The type name
     */
    public String getDocClass(String type) {
        return this.get(type);
    }

    /**
     * Sets for each object type found the segment / node i
     *
     * @param lii List of Integrity Items
     */
    public void setDocumentClass(List<IntegrityItem> lii) {

        IntegrityAPI ia = new IntegrityAPI();

        ia.connectCommonSession();
        // add unique entries
        for (IntegrityItem ii : lii) {
            this.put(ii.getType(), "**");
        }

        // Add the column value 'documentClass'
        for (Map.Entry<String, String> entry : this.entrySet()) {
            WorkItem wi = ia.getTypeWI(entry.getKey());
            String docClass = wi.getField("documentClass").getValueAsString();
            entry.setValue(docClass);
        }
        // List the entries
        // for (Map.Entry<String, String> entry : this.entrySet()) {
        //     ia.log(entry.getKey()+" = "+entry.getValue());
        // }
        ia.releaseCmdRunner();
    }

    /**
     * Returns the appropriate create command for the type/docClass
     *
     * @param type Integrity Item Type
     * @return a string with the appropriate create command
     */
    public String getCreateCmd(String type) {
        if (this.getDocClass(type).contentEquals("segment")) {
            return ("|im|createsegment|" + type + "|");
        } else if (this.getDocClass(type).contentEquals("node")) {
            return ("|im|createcontent|" + type + "|");
        }
        return "|im|createissue|" + type + "|";
    }
}
