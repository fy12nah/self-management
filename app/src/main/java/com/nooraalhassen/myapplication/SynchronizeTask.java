package com.nooraalhassen.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nooraalhassen.myapplication.model.Category;
import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Exercise;
import com.nooraalhassen.myapplication.model.ExerciseType;
import com.nooraalhassen.myapplication.model.Illness;
import com.nooraalhassen.myapplication.model.IllnessName;
import com.nooraalhassen.myapplication.model.Meal;
import com.nooraalhassen.myapplication.model.Mood;
import com.nooraalhassen.myapplication.model.MoodName;
import com.nooraalhassen.myapplication.model.Physical;
import com.nooraalhassen.myapplication.model.Profile;
import com.nooraalhassen.myapplication.model.Sleeping;
import com.nooraalhassen.myapplication.model.User;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by nooraalhassen
 */
public class SynchronizeTask extends AsyncTask<Void, Void, Void> {

    Context context;

    public SynchronizeTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {


        DBmanager db = new DBmanager(context);

        SharedPreferences preferences = context.getSharedPreferences(Constants.sharedpreferencesId, 0);
        long user_id = preferences.getLong(Constants.userId, -1);

        User u = db.getUser(user_id);
        Profile p = db.getProfile(user_id);
        Physical ph = db.getPhysical(user_id);
        Mood mood = db.getMood(user_id);
        MoodName mn = db.getMoodName(user_id);
        Sleeping slp = db.getSleeping(user_id);
        Exercise ex = db.getExercise(user_id);
        ExerciseType et = db.getExerType(user_id);
        Illness i = db.getIllness(user_id);
        IllnessName sin = db.getSTIName(user_id);
        IllnessName lin = db.getLTIName(user_id);
        Meal m = db.getMeal(user_id);
        Category c = db.getCat(user_id);


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = "";


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String format = "json";

        try {

            final String SYNC_URL =
                    "http://192.168.0.5:8080/ProjectBackend/sync";
            final String USER_PARAM = "userId";
            final String SYNC_PARAM = "syncWhat";
            final String DATA_PARAM = "data";


            HashMap<String, String> postDataParams = new HashMap<>();

            json = gson.toJson(u);
            postDataParams.put(USER_PARAM, String.valueOf(user_id));
            postDataParams.put(SYNC_PARAM, "User");
            postDataParams.put(DATA_PARAM, json);
            String response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(p);
            postDataParams.put(SYNC_PARAM, "Profile");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(ph);
            postDataParams.put(SYNC_PARAM, "Physical");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(slp);
            postDataParams.put(SYNC_PARAM, "SleepingHours");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(mood);
            postDataParams.put(SYNC_PARAM, "MoodStatus");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(mn);
            postDataParams.put(SYNC_PARAM, "MoodName");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(et);
            postDataParams.put(SYNC_PARAM, "ExerciseType");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(ex);
            postDataParams.put(SYNC_PARAM, "Exercise");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(m);
            postDataParams.put(SYNC_PARAM, "Meal");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);


            json = gson.toJson(i);
            postDataParams.put(SYNC_PARAM, "Illness");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(sin);
            postDataParams.put(SYNC_PARAM, "Short-IllnessName");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(lin);
            postDataParams.put(SYNC_PARAM, "Long-IllnessName");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);

            json = gson.toJson(c);
            postDataParams.put(SYNC_PARAM, "Category");
            postDataParams.put(DATA_PARAM, json);
            response = performPostCall(SYNC_URL, postDataParams);


            Log.d("Noora Networking", "connected..." + response);


        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }


        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }


    public String  performPostCall(String requestURL,
                                   HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


    private void readStream(InputStream in) {
        byte[] bytes =new byte [1024];
        try {
            in.read(bytes);
            String s = new String (bytes);
            Log.d("Noora server response", s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}