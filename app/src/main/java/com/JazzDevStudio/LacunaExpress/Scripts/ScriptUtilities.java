package com.JazzDevStudio.LacunaExpress.Scripts;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Body;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Captcha;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan.GetAccount;

/**
 * Created by Alma on 1/30/2015.
 */
class ScriptUtilities {
    static String GetBuildingID(String displayName, String buildingName, String planetName){
        AccountInfo account = GetAccount(displayName);
        String planetID = "0";
        Set<String> idSet = account.colonies.keySet();
        for(String i: idSet){
            if(account.colonies.get(i).equals(planetName)){
                planetID = i;
                break;
            }
        }
        String request =  Body.GetBuildings(1, account.sessionID, planetID);
        AsyncServer s = new AsyncServer();
        request = s.ServerRequest(account.server, Captcha.url, request);
        Gson gson = new Gson();
        Response response = gson.fromJson(request, Response.class);

        if(response.result.buildings.containsValue(buildingName)){
            idSet.clear();
            idSet = response.result.buildings.keySet();
            for(String i: idSet){
                if(response.result.buildings.get(i).name.equals(buildingName)){

                    //may need to also implment a check if building is damaged because functionality of building may be blocked.
                    return i;
                }
            }
        }


        return null;
    }
}
