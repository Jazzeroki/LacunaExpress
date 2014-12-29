package com.JazzDevStudio.LacunaExpress.MISCClasses;

import android.os.AsyncTask;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Empire;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;


/*
 * This class is setup to provide utilities for refreshing Session Date
 */
public class sessionRefresh extends AsyncTask<String, Void, Void> {

	@Override
	protected Void doInBackground(String... params) {
		ArrayList<AccountInfo> accounts = AccountMan.GetAccounts();
		
		if(accounts.size()>0){
			String r = "";
			ArrayList<AccountInfo> refreshedAccounts = new ArrayList<AccountInfo>();
			AccountInfo account;
			Gson gson = new Gson();
			
			for(AccountInfo i: accounts){
				Empire e = new Empire();
				r = e.Login(i.userName, i.password, 1);
				AsyncServer s = new AsyncServer();
				r = s.ServerRequest(i.server, Empire.url, r);
				
				if(!r.equals("error")){
					Response response = gson.fromJson(r, Response.class);
					account = i;

                    //pulling updated info from response and saving it into account info.
					account.sessionID = response.result.session_id;
                    account.colonies = response.result.status.empire.colonies;
                    account.stations = response.result.status.empire.stations;
                    account.bodiesCombined = response.result.status.empire.planets;
                    account.homePlanetID = response.result.status.empire.home_planet_id;
					Log.d("MiscClasses.sessionRefresh", "new sessionID "+account.sessionID);
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.HOUR, 2);
                    account.sessionExpires = c;
					refreshedAccounts.add(account);
				}
				else
					refreshedAccounts.add(i);
			}
			AccountMan.Save(refreshedAccounts);
		}
		return null;
	}

}
