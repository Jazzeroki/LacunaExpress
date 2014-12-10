package MISCClasses;

import java.util.ArrayList;

import com.google.gson.Gson;

import AccountMan.AccountInfo;
import AccountMan.AccountMan;
import JavaLEWrapper.Empire;
import LEWrapperResponse.Response;
import Server.AsyncServer;
import android.os.AsyncTask;
import android.util.Log;

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
					account.sessionID = response.result.session_id;
					Log.d("MiscClasses.sessionRefresh", "new sessionID "+account.sessionID);
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
