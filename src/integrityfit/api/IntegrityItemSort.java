/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.api;

import java.util.Comparator;

/**
 * A comperator for IntegrityItem
 *
 * @author veckardt
 */
public class IntegrityItemSort implements Comparator<IntegrityItem> {

    /**
     * Returns 0, -1 and 1 in case of same, lower greater
     *
     * @param ii1 The IntegrityItem to compare
     * @param ii2 The IntegrityItem to compare with
     * @return an integer value
     */
    @Override
    public int compare(IntegrityItem ii1, IntegrityItem ii2) {
        return ii1.isGreater2(ii2);
    }
}
