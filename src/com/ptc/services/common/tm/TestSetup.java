/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptc.services.common.tm;

import com.mks.api.Session;
import com.mks.api.response.APIException;
import com.ptc.services.common.api.IntegrityAPI;

/**
 *
 * @author veckardt
 */
public class TestSetup {

    public static TestResultFieldList testResultFields = new TestResultFieldList();

    public static void init(IntegrityAPI imSession) throws APIException {
        testResultFields.init(imSession);
    }
}
