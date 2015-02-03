package com.JazzDevStudio.LacunaExpress.Captcha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Captcha;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.CheckInternetConnection;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;

import static com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan.GetAccount;
import static com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Captcha.Solve;

/**
 * Created by Alma on 1/27/2015.
 */
public class CheckCaptcha extends AsyncTask<String, Void, Void> {
    private final String displayName;
    private Context context;
    public CheckCaptcha(String displayName, Context context){
        this.displayName = displayName;
        this.context = context;
    }
    @Override
    protected Void doInBackground(String[] params) {
        if(CheckInternetConnection.haveNetworkConnection(context)){
            AccountInfo account = GetAccount(displayName);
            String r = Captcha.Fetch(account.sessionID);
            Log.d("CheckCaptcha", r);
            AsyncServer s = new AsyncServer();
            r = s.ServerRequest(account.server, Captcha.url, r);
            Gson gson = new Gson();
            Response res = gson.fromJson(r, Response.class);

            //ImageView img = (ImageView) findViewById(R.id.imageView1);
            try {
                Log.d("CheckCaptcha", "Downloading Image");
                URL url = new URL(res.result.url);
                //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
                HttpGet httpRequest = null;

                httpRequest = new HttpGet(url.toURI());

                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = (HttpResponse) httpclient
                        .execute(httpRequest);

                HttpEntity entity = response.getEntity();
                BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                InputStream input = b_entity.getContent();

                Bitmap bitmap = BitmapFactory.decodeStream(input);

                //Starting UI
                //Log.d("CheckCaptcha", "Starting Intent");
                Intent openActivity = new Intent(context, com.JazzDevStudio.LacunaExpress.Captcha.Captcha.class);
                openActivity.putExtra("displayString", account.displayString);
                //openActivity.putExtra("imageURL", response.result.url);
                openActivity.putExtra("guid", res.result.guid);
                //Log.d("CheckCaptcha", "inserting image");
                openActivity.putExtra("image", bitmap);
                //context.startAc
                //need to watch this as this likely should not work.
                //((Activity)context).startActivityForResult(openActivity, 1);
                context.startActivity(openActivity);

                //img.setImageBitmap(bitmap);

            } catch (Exception ex) {

            }




        }
        return null;
    }



   /* public static Boolean CheckCaptcha(String displayName, Context context){
        if(CheckInternetConnection.haveNetworkConnection(context)){
            AccountInfo account = GetAccount(displayName);

        }*/


}
class CheckAnswer extends AsyncTask<Void, Void, Void>{
    private final String displayName, answer, guid;
    private Context context;
    CheckAnswer(Context context, String displayName, String answer, String guid){
        Log.d("CheckAnswer guid", guid);
        Log.d("CheckAnswer displayname", displayName);
        Log.d("CheckAnswer answer", answer);
        this.displayName = displayName;
        this.context = context;
        this.answer = answer;
        this.guid = guid;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Log.d("CheckAnswer.doinbackground", "getting Account");
        AccountInfo account = GetAccount(displayName);
        Log.d("CheckAnswer sessionID", account.sessionID);
        Log.d("CheckAnswer creating solution string", "creating solution string");
        String r = Solve(account.sessionID, guid, answer);
        Log.d("CheckCaptcha.Solve", r);
        AsyncServer s = new AsyncServer();
        r = s.ServerRequest(account.server, Captcha.url, r);
        Gson gson = new Gson();
        CaptchaResponse res = gson.fromJson(r, CaptchaResponse.class);
        if(res.result == 1){
            //still need to implement what happens if result is bad or good
            //if good result will ==1
        }

        //returning null seems to be causing and error need to change return type later
        return null;
    }

}