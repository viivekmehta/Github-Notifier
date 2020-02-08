package com.example.githubnotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    public void createWebhook(final HashMap randomNumbers) {
        runOnUiThread(new Runnable() {
            public void run() {
                Log.i("random number : ","hey the number in webhook is --> "+randomNumbers.get("randomNumber"));
                Toast.makeText(getBaseContext(),"hey the random number is --> "+randomNumbers.get("randomNumber"),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getRandomNumberFromServer(View view) {
        final String data = "{"+
                "\"name\":\" vivek mehtaa\""+
                "}";
        String url = "http://041e6194.ngrok.io/getRandomNumber";
        final HashMap<String,String> randomNumbers = new HashMap<String,String>();
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonRequest jsonRequest = new JsonRequest(Request.Method.POST,
                url,
                null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error","Hey "+ error +"!!");
                        Toast.makeText(MainActivity.this, "Hey "+ error +"!!", Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody()  {
                try{
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                randomNumbers.put("randomNumber","okok"+(new String(response.data)).toString());
                Log.i("random number : ","hey the number in response is --> "+randomNumbers.get("randomNumber"));
                createWebhook(randomNumbers);
                return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        requestQueue.add(jsonRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
