package com.example.quizzie_final;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.res.AssetManager;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.util.Random;
import java.util.List;
public class Questions {
    private static Random rand = new Random();
    private static JSONObject questionBank;
    public static void loadJSONFromAsset(Context context) throws JSONException {
        String json = null;
        try {
            InputStream is = context.getAssets().open("qa.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        questionBank = new JSONObject(json);
    }
    // generate random question by topic and difficulty
    public static JSONObject[] getQuestions(String topic, String difficulty) throws JSONException {
        JSONArray all =  questionBank.getJSONObject(topic).getJSONArray(difficulty);
        int[] ranNum = new int[5];
        JSONObject[] list = new JSONObject[5];
        // generate 5 unique random numbers
        for (int i =0; i<5; i++){
            boolean b = true;
            while (b){
                ranNum[i] = rand.nextInt(all.length());
                for (int j=0; j<i; j++){
                    if (ranNum[j] == ranNum[i]){
                        b = true;
                        break;
                    }
                    if (j>= i){
                        b = false;
                    }
                }
            }
            list[i] = all.getJSONObject(ranNum[i]);
        }
        return list;
    }
}
