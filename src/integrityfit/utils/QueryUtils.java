/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Some special methods to manage the query parameter for integrity
 *
 * @author veckardt
 */
public class QueryUtils {

    /**
     * Adds Prefix if not empty
     *
     * @param input the base string to check
     * @param prefix the additional text to add as prefix
     * @param string the last part to add if input is empty
     * @return The string to be used
     */
    public static String addIfNull(String input, String prefix, String string) {
        if ((input == null) || (input.isEmpty())) {
            return (string);
        }
        return (input + "" + prefix + "" + string);
    }

    /**
     * Returns for each itemID the corresponding field[ID]=nnn
     *
     * @param itemString A list of item ids
     * @return the corresponding query string
     */
    public static String getSubQuery(String itemString) {
        String subquery = "";
        List<String> aid;

        if (itemString.contains("-")) {
            aid = Arrays.asList(itemString.trim().split("\\s*-\\s*"));
            subquery = "(field[ID]>=" + aid.get(0) + " and field[ID]<=" + aid.get(1) + ")";
        } else {
            aid = Arrays.asList(itemString.trim().split("\\s*,\\s*"));
            for (int i = 0; i < aid.size(); i++) {
                if (i > 0) {
                    subquery = subquery.concat(" or ");
                }
                subquery = subquery.concat("(field[ID]=" + aid.get(i) + ")");
            }
        }
        System.out.println(subquery);
        return subquery;
    }
}
