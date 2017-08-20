package com.riteshmohapatra.talkingwiki;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton search;
    EditText etsearch;
    HttpGet httpGet;
    HttpClient httpClient;
    HttpResponse response;
    String CHECK_URL = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&titles=";
    JSONObject json;
    TextToSpeech ts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (ImageButton)findViewById(R.id.btnsearch);
        etsearch = (EditText)findViewById(R.id.etsearch);
        ts = new TextToSpeech(this,new ConvertSpeech());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,etsearch.getText().toString()+"",Toast.LENGTH_SHORT).show();
                String query  =  etsearch.getText().toString();
                httpGet  = new HttpGet(CHECK_URL+query);

                httpClient = new DefaultHttpClient();
                try {
                    response = httpClient.execute(httpGet);

                    int status = response.getStatusLine().getStatusCode();

                    if (status == 200) {
                        HttpEntity entity = response.getEntity();
                        String data = EntityUtils.toString(entity);


                        json = new JSONObject(data);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    class ConvertSpeech implements TextToSpeech.OnInitListener{

        @Override
        public void onInit(int status) {
            ts.setLanguage(Locale.US);
        }
    }
}
