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
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    public void createWebhook(final HashMap randomNumbers) {
        final String url = "https://api.github.com/repos/viivekmehta/Messenger/hooks";
        runOnUiThread(new Runnable() {
            public void run() {
                final String data = "{"+
                        "\"name\": \"web\","+
                        "\"active\": true,"+
                        "\"events\": ["+
                                        "\"push\","+
                                         "\"pull_request\""+
                                    "],"+
                        "\"config\": {"+
                                    "\"url\": \"http://example.com/webhook\","+
                                     "\"content_type\": \"json\""+
                                    "}"+
                        "}";
                Log.i("random number : ","hey the number in webhook is --> "+randomNumbers.get("randomNumber"));
                Toast.makeText(getBaseContext(),"hey the random number is --> "+randomNumbers.get("randomNumber"),Toast.LENGTH_LONG).show();
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
                    public byte[] getBody()  {
                        try{
                            return data == null ? null : data.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            return null;
                        }
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                        params.put("Authorization", "4329649ab3af9c1a19660744570f80eccaacba42 OAUTH-TOKEN");
                        return params;
                    }

                    @Override
                    protected Response parseNetworkResponse(NetworkResponse response) {
                        randomNumbers.put("randomNumber","okok"+(new String(response.data)).toString());
                        Log.i("random number : ","hey the number in response is --> "+randomNumbers.get("randomNumber"));
                        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                requestQueue.add(jsonRequest);
            }
        });
    }

    public void getRandomNumberFromServer(View view) {
        final String data = "{"+
                "\"name\":\"Mr. Vivek Mehta\""+
                "}";
        String url = "http://ad18fa9f.ngrok.io/getRandomNumber";
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
            public byte[] getBody()  {
                try{
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
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
