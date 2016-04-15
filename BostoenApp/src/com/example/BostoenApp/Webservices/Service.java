package com.example.BostoenApp.Webservices;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.BostoenApp.DB.CustomDate;
import com.example.BostoenApp.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by david on 15/04/2016.
 */
public class Service {
    private Activity activity;

    public Service(Activity activity) {
        this.activity = activity;
    }


    public boolean userExist() throws ExecutionException, InterruptedException {
        Call call = new Call();
        JSONObject response =call.execute("http://bostoen.info/api/scorecard/user/read", "[]").get();
        if(response!=null){
            Log.d("response",response.toString());
        }
        else {
            Log.d("response","null");
        }


        return false;
    }
    public boolean isOnline()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return ((networkInfo != null) && networkInfo.isConnected());
    }



    private class Call extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            if(isOnline())
            {
                HttpClient httpclient = new DefaultHttpClient();

                String url = params[0];
                String data = params[1];
                HttpPost httppost = new HttpPost(url);


                try {
                    // Add your data
                    Date date = new CustomDate();
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("auth", activity.getString(R.string.public_key)));
                    nameValuePairs.add(new BasicNameValuePair("secret", getSecret(date)));
                    nameValuePairs.add(new BasicNameValuePair("time", date.toString()));
                    nameValuePairs.add(new BasicNameValuePair("data", data));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                    ResponseHandler<String> responseHandler=new BasicResponseHandler();
                    // Execute HTTP Post Request
                    String responseString = httpclient.execute(httppost,responseHandler);

                    Log.d("responseString",responseString);
                    jsonObject = new JSONObject(responseString);

                } catch (ClientProtocolException e) {
                    Log.d("ClientProtocolException",e.getMessage());
                } catch (IOException e) {
                    Log.d("IOException",e.getMessage());

                } catch (JSONException e) {
                    Log.d("JSONException", e.getMessage());
                } catch (NoSuchAlgorithmException e) {
                    Log.d("NoSuchAlgorithmException", e.getMessage());
                }
            }

            return jsonObject;
        }
    }

    private String getSecret(Date date) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String plaintext = date.toString() + activity.getString(R.string.private_key);
        messageDigest.update(plaintext.getBytes());

        String output =  new BigInteger(1, messageDigest.digest()).toString(16);
        Log.d("datestring",date.toString());
        Log.d("private key",activity.getString(R.string.private_key));
        Log.d("encoded", output);
        return output;
    }
}