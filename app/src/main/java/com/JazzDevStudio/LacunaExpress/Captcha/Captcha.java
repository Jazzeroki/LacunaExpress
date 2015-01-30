package com.JazzDevStudio.LacunaExpress.Captcha;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;

public class Captcha extends Activity implements View.OnClickListener, serverFinishedListener {
    private Button button;
    private EditText text;
    private ImageView image;
    private Intent i;
    //private Bundle bundle;

    private void Initialize(){
        Log.d("CheckCaptcha", "Initializing UI");
        button = (Button) findViewById(R.id.buttonCaptchaAnswer);
        text = (EditText) findViewById(R.id.editTextCaptchaAnswer);
        image = (ImageView) findViewById(R.id.imageViewAnswerCaptcha);

        button.setOnClickListener(this);

        Intent i = getIntent();
        Bitmap bitmap = (Bitmap) i.getParcelableExtra("image");

        image.setImageBitmap(bitmap);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        Initialize();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_captcha, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        text.toString();
        //CheckAnswer(String displayName, Context context, String answer, String guid)
        CheckAnswer c = new CheckAnswer(this, getIntent().getStringExtra("displayName"),text.getText().toString(), getIntent().getStringExtra("guid"));
        //CheckCaptcha.CheckAnswer c = new CheckCaptcha.CheckAnswer(getIntent().getStringExtra("displayName"),this, text.getText(), getIntent().getStringExtra("guid"));

	    //Execute the checkAnswer
	    c.execute();
    }

    @Override
    public void onResponseReceived(String reply) {

    }
}


/*
image download code

ImageView img = (ImageView) findViewById(R.id.imageView1);
try {
        URL url = new URL("Your URL");
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

        img.setImageBitmap(bitmap);

    } catch (Exception ex) {

    }
 */