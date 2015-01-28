package com.JazzDevStudio.LacunaExpress.Captcha;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.MISCClasses.CheckInternetConnection;

/**
 * Created by Alma on 1/27/2015.
 */
public class CheckCaptcha {
    public static Boolean CheckCaptcha(String displayName){
        //if(CheckInternetConnection.haveNetworkConnection())
        AccountMan.GetAccount(displayName);
        /*
    load account file and check if session and captcha are still valid

    if not load captcha activity otherwise return true

    After activity completes return true
     */

        return null;
    }
}
