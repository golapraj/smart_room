package com.akibgolap.kuet.SmartRoom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.ImageButton;
import android.os.AsyncTask;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class Main22Activity extends AppCompatActivity {

    public static String PREF_IP = "PREF_IP_ADDRESS";
    public static String PREF_PORT = "PREF_PORT_NUMBER";
    public static String PREF_ROOM= "PREF_ROOM_ADDRESS";

    private ImageButton doorButton,lightButton,bedRoomButton,dimlightButton,fanButton,captureImage;
    private WebView wv1;

    ProgressDialog progressBar;

    Animation buttonFadein, buttonBlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        buttonFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        buttonBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

        PREF_IP = getIntent().getStringExtra("ARDUINO_IP");
        PREF_PORT = getIntent().getStringExtra("ARDUINO_PORT");
        PREF_ROOM = getIntent().getStringExtra("ROOM_ADDRESS");


        String roomUrl = PREF_ROOM;
        roomUrl = "http://"+roomUrl+":8080/jsfs.html";


        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setTitle("Sending Signal to Room");
        progressBar.setMessage("Please wait...");

        doorButton = (ImageButton) findViewById(R.id.doorButton);
        lightButton = (ImageButton) findViewById(R.id.lightButton);
        bedRoomButton = (ImageButton) findViewById(R.id.bedRoomButton);
        dimlightButton = (ImageButton) findViewById(R.id.dimlightButton);
        fanButton = (ImageButton) findViewById(R.id.fanButton);
        captureImage = (ImageButton) findViewById(R.id.captureButton);

        doorButton.setAnimation(buttonFadein);
        lightButton.setAnimation(buttonFadein);
        bedRoomButton.setAnimation(buttonFadein);
        dimlightButton.setAnimation(buttonFadein);
        fanButton.setAnimation(buttonFadein);


        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());

        doorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();
                String arduinoIP = PREF_IP;
                String arduinoPort = PREF_PORT;

                String parameterValue = "10";

                if (arduinoIP.length() > 0 && arduinoPort.length() > 0) {
                    new HttpRequestAsyncTask(
                            v.getContext(), parameterValue, arduinoIP, arduinoPort, "pin"
                    ).execute();
                }

            }
        });

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String streamIP = PREF_ROOM;
                Intent intent = new Intent(Main22Activity.this,Main2Activity.class);
                intent.putExtra("ROOM_ADDRESS",streamIP);
                startActivity(intent);
            }
        });

        fanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();

                String arduinoIP = PREF_IP;
                String arduinoPort = PREF_PORT;

                String parameterValue = "11";

                if (arduinoIP.length() > 0 && arduinoPort.length() > 0) {
                    new HttpRequestAsyncTask(
                            v.getContext(), parameterValue, arduinoIP, arduinoPort, "pin"
                    ).execute();
                }

            }
        });


        final String finalRoomUrl = roomUrl;
        bedRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = finalRoomUrl;
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                wv1.getSettings().setPluginState(WebSettings.PluginState.ON);
                wv1.setWebChromeClient(new WebChromeClient());
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });



        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();

                String arduinoIP = PREF_IP;
                String arduinoPort = PREF_PORT;

                String parameterValue = "12";

                if (arduinoIP.length() > 0 && arduinoPort.length() > 0) {
                    new HttpRequestAsyncTask(
                            v.getContext(), parameterValue, arduinoIP, arduinoPort, "pin"
                    ).execute();
                }

            }
        });


        dimlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();

                String arduinoIP = PREF_IP;
                String arduinoPort = PREF_PORT;

                String parameterValue = "13";

                if (arduinoIP.length() > 0 && arduinoPort.length() > 0) {
                    new HttpRequestAsyncTask(
                            v.getContext(), parameterValue, arduinoIP, arduinoPort, "pin"
                    ).execute();
                }

            }
        });







        String url = finalRoomUrl;


        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv1.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv1.setWebChromeClient(new WebChromeClient());
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);
    }

    public String sendRequest(String parameterValue, String arduinoIP, String arduinoPort, String parameterName) {
        String serverResponse = "ERROR";

        try {

            HttpClient httpclient = new DefaultHttpClient();
            URI website = new URI("http://" + arduinoIP + ":" + arduinoPort + "/?" + parameterName + "=" + parameterValue);
            HttpGet getRequest = new HttpGet();
            getRequest.setURI(website);
            HttpResponse response = httpclient.execute(getRequest);
            InputStream content = null;
            content = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    content
            ));
            serverResponse = in.readLine();
            content.close();
        } catch (ClientProtocolException e) {
            // HTTP error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // IO error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // URL syntax error
            serverResponse = e.getMessage();
            e.printStackTrace();
        }
        // return the server's reply/response text
        return serverResponse;
    }


    private class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private String requestReply, arduinoIP, arduinoPort;
        private Context context;
        //private AlertDialog alertDialog;
        private String parameter;
        private String parameterValue;

        public HttpRequestAsyncTask(Context context, String parameterValue, String arduinoIP, String arduinoPort, String parameter) {
            this.context = context;


         //   Toast toast1 = Toast.makeText(getApplicationContext(),"Connecting to Room",Toast.LENGTH_LONG);
         //   toast1.show();
            this.arduinoIP = arduinoIP;
            this.parameterValue = parameterValue;
            this.arduinoPort = arduinoPort;
            this.parameter = parameter;
        }



        @Override
        protected Void doInBackground(Void... params) {
            requestReply = sendRequest(parameterValue, arduinoIP, arduinoPort, parameter);
            if(requestReply != null)
            {
                try {
                    Thread.sleep(3000);
                    progressBar.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Toast toast = Toast.makeText(getApplicationContext(),requestReply,Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        protected void onPreExecute() {

           // Toast toast = Toast.makeText(getApplicationContext(),"Sending data to Room, please wait...",Toast.LENGTH_SHORT);
           // toast.show();

        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main22, menu);
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
}
