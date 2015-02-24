package com.JazzDevStudio.LacunaExpress.Scripts;

import android.app.IntentService;
import android.content.Intent;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Security;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.google.gson.Gson;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class SpyScripts extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTIONS_EXECUTE_ALL_CAPTURED_SPIES = "execute spies";
    public static final String ACTION_FOO = "com.JazzDevStudio.LacunaExpress.Scripts.action.FOO";
    public static final String ACTION_BAZ = "com.JazzDevStudio.LacunaExpress.Scripts.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.JazzDevStudio.LacunaExpress.Scripts.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.JazzDevStudio.LacunaExpress.Scripts.extra.PARAM2";
    private Gson gson = new Gson();
    public SpyScripts() {
        super("SpyScripts");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(ACTIONS_EXECUTE_ALL_CAPTURED_SPIES.equals(action)){
                //ScriptUtilities.GetBuildingID(intent.getStringExtra("displayName"), intent.getStringExtra("planetName")); //This is giving a syntax error
            }
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private void ExecuteAllCapturedSpies(AccountInfo account, String PlanetID, String buildingID){
        String r = Security.ViewPrisoners(account.sessionID, buildingID, "1");
        AsyncServer s = new AsyncServer();
        r = s.ServerRequest(account.server, Security.url, r);
        //String reply = server.ServerRequest(gameServer, security.url, request);
        Response response = gson.fromJson(r, Response.class);
        if(!response.result.captured_count.contentEquals("0"))
            for(Response.Prisoner p: response.result.prisoners){
                //Security s = new Security("security");
                r = Security.ExecutePrisoner(account.sessionID, buildingID, p.id);
                s.ServerRequest(account.server, Security.url, r);
                //System.out.println("Prisoner: "+p.name+" Executed for "+p.task);
            }
    }
}
