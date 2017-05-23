/*
 * Copyright:      Copyright 2015 (c) Parametric Technology GmbH
 * Product:        PTC Integrity Lifecycle Manager
 * Author:         V. Eckardt, Senior Consultant ALM
 * Purpose:        Custom Developed Code
 * **************  File Version Details  **************
 * Revision:       $Revision$
 * Last changed:   $Date$
 */
package integrityfit;

import com.mks.api.Command;
import static java.lang.System.out;

/**
 *
 * @author veckardt
 */
public class IntegrityTest {

    public static void main(String[] args) {
        IntegrityFitNesse ifn = new IntegrityFitNesse();
        
        ifn.init(Command.IM);
        ifn.setCommand("createissue");
        ifn.setField("Summary", "sum");
        ifn.setField("Project", "/Projects/STeF");
        ifn.setField("Description", "some description");
        ifn.setParam(Config.fnType, "Defect");
        ifn.execute();
        out.println(ifn.getLastMessage());
        out.println(ifn.getFieldValue());
    }

}
