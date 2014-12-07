package Server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AsyncServer extends AsyncTask<ServerRequest, Void, String> {
    List<serverFinishedListener> listeners = new ArrayList<serverFinishedListener>();
    private String output = "";
    public void addListener(serverFinishedListener toAdd){
        listeners.add(toAdd);
    }
	
    protected String doInBackground(ServerRequest... a){
		
    	ServerRequest(a[0].server, a[0].methodURL, a[0].json);
    	//ResponseReceived();
    	return output;
	}
    
    private void ResponseReceived(){
    	Log.d("Firing Event", "Sending out response to listeners");
        for(serverFinishedListener i: listeners){
            i.onResponseRecieved(output);
        }
        listeners.clear();
    }
    @Override
    protected void onPostExecute(String r){
    	Log.d("OnPostExecute", "Firing On Post Execute");
    	ResponseReceived();

    }
	
    private String ServerRequest(String gameServer, String methodURL, String JsonRequest) {
        //String output = "";
        try {
            try { //putting thread to sleep for just over a second to throttle client because of the limit of 60 calls per minute
            	Log.d("Throttle Pause", "Pausing for server throttling");
                Thread.sleep(1100);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            Log.d("URL", (gameServer+"/" + methodURL));
            //System.out.println((gameServer+"/" + methodURL));
            //SaveToLog("Request");
            //SaveToLog((gameServer+"/" + methodURL));
            //SaveToLog(JsonRequest);
            //SaveToLog("");
            URL url = new URL(gameServer+"/" + methodURL);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(JsonRequest);
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            output = in.readLine();
            Log.d("Server reply", output);
            //ResponseRecieved();
            //SaveToLog("Reply");
            //SaveToLog(output);
            //SaveToLog("");

        } catch (java.net.MalformedURLException e) {
            Log.d("Server Error", "Malformed URL Exception");
            output = "error";
            //ResponseRecieved();
            //SaveToLog("Reply");
            //SaveToLog("Malformed URL Exception");
            //SaveToLog(output);
            //SaveToLog(e.getMessage());
        } catch (java.io.IOException e) {
        	Log.d("Server Error", "Malformed IO Exception");
            output = "error";
            //ResponseRecieved();
            //SaveToLog("Reply");
            //SaveToLog("IO Exception");
            //SaveToLog(output);
            //SaveToLog(e.getMessage());
        }
        return output;

    }
    /*  Seems PrintWriter is not supported in Android
    private void SaveToLog(String log){
        try(PrintWriter printWriter = new PrintWriter(new FileWriter("IO.log", true))) {
            printWriter.println(log);
        }catch (IOException e) {

        }
    }*/
}
