/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import com.mks.api.response.Field;
import com.mks.api.response.Item;
import com.mks.api.response.ItemList;
import com.mks.api.response.WorkItem;
import integrityfit.utils.FieldListArray;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Special object to hold the Integrity change set, which is stored as an item
 *
 * @author veckardt
 */
public class IntegrityItem { //  implements Item{

    // Item Info
    private String type;
    private String Id;
    private String refeId;
    // Change Record Info
    public Date creationDate;
    public Item item;
    public Date modifiedDate;
    public Map<String, String> sharedFieldList = new HashMap<String, String>();
    public FieldListArray colList = null;

    /**
     *
     * @param ii
     * @param wi
     */
    public IntegrityItem(WorkItem wi, Item ii, String modelClass) {
        // Item Information
        this.type = wi.getField("Type").getValueAsString();
        // for(String s : this.type.split("\\s+")) System.out.println(s.charAt(0));
        this.creationDate = wi.getField("Created Date").getDateTime();

        // Change Record Information
        this.item = ii;
        this.modifiedDate = ii.getField("MKSIssueModifiedDate").getDateTime(); // wi.getField("Modified Date").getDateTime();
        this.Id = wi.getId();
        //
        try {
            ItemList iRefe = (ItemList) wi.getField("References").getList();
            Item im = (Item) iRefe.get(0);
            // for (Iterator it = iRefe.iterator(); it.hasNext();) {
            //    Item im = (Item)it.next();
            refeId = im.getId();

            //   break;
            // }         
        } catch (NoSuchElementException ex) {
            refeId = null;
        }
    }

    /**
     * Returns a constructed type short name for the return values such as
     * $ARSID=
     *
     * @return A type short name
     */
    public String getTypeShortName() {
        String typeShortName = "";
        String tt = this.type.replaceAll("_", " ");
        for (String s : tt.split("(?<=[\\S])[\\S]+")) {
            typeShortName = typeShortName.concat(s.trim());
        }
        return typeShortName;
    }

    public boolean isGreater(IntegrityItem ii) {
        return (this.modifiedDate.after(ii.modifiedDate));
    }

    public int isGreater2(IntegrityItem ii) {
        return (this.modifiedDate.compareTo(ii.modifiedDate));
    }

    public String getType() {
        return this.type;
    }

    public String getRefeId() {
        return this.refeId;
    }

    public String getIdAndRefeId() {
        if (this.refeId == null) {
            return this.Id;
        }
        return this.Id + "/" + this.refeId;
    }

    public String getId() {
        return this.Id;
    }

    // This returns the same as getId() !
    // public String getContext() {
    //     return this.item.getContext();
    // }
    public Field getField(String string) {
        return this.item.getField(string);
    }
}
