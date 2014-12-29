package com.JazzDevStudio.LacunaExpress.Server;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import com.JazzDevStudio.LacunaExpress.MISCClasses.CheckInternetConnection;


/**
 * Created by Alma on 12/23/2014.
 * requires the following extras
 * gameServer
 * url
 * request
 */
public class ServerIntentService extends IntentService {
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null && extras.containsKey("gameServer")&& extras.containsKey("url")&& extras.containsKey("request")) {
            if (CheckInternetConnection.haveNetworkConnection(this)) {
                String output, request, gameServer, methodURL;
                //String output = "";
                try {
                    try { //putting thread to sleep for just over a second to throttle client because of the limit of 60 calls per minute
            	        //Log.d("Throttle Pause", "Pausing for server throttling");
                        Thread.sleep(1300);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    request = extras.getString("request");
                    gameServer = extras.getString("gameServer");
                    methodURL = extras.getString("url");

                    Log.d("AsyncServer.ServerRequest URL", (gameServer + "/" + methodURL));
                    Log.d("AsyncServer.ServerRequest","Request string "+request);
                    URL url = new URL(gameServer+"/" + methodURL);
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(request);
                    out.close();
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    output = in.readLine();
                    Log.d("AsyncServer.ServerRequest","Reply string "+output);
                } catch (java.net.MalformedURLException e) {
                    Log.d("Server Error", "Malformed URL Exception");
                    output = "error";
                } catch (java.io.IOException e) {
                    Log.d("Server Error", "Malformed IO Exception");
                    output = "error";
                }
                //return output;

            }
            else {

            }
        }
    }

    public ServerIntentService() {
        super("ServerIntentService");

    }
}
