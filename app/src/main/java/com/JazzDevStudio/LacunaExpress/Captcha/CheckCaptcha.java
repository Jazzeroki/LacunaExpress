package com.JazzDevStudio.LacunaExpress.Captcha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Empire;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.CheckInternetConnection;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Captcha;
import com.JazzDevStudio.LacunaExpress.PlanetOptions;
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

import static com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan.*;

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
                Log.d("CheckCaptcha", "Starting Intent");
                Intent openActivity = new Intent(context, com.JazzDevStudio.LacunaExpress.Captcha.Captcha.class);
                //openActivity.putExtra("displayString", account.displayString);
                //openActivity.putExtra("imageURL", response.result.url);
                //openActivity.putExtra("guid", response.result.guid);
                openActivity.putExtra("image", bitmap);
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