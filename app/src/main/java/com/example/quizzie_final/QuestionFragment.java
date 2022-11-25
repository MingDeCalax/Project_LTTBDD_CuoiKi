package com.example.quizzie_final;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static JSONObject[] list = new JSONObject[5];
    private static Random rand = new Random();
    private static JSONObject questionBank;
    public static int currentQuestionCount = 0;
    public static int currentQuestionCorrects = 0;
    private static int correctAnsPos = 1;
    private static String currentQuestion = "Empty";
    private static String[] answers = {"A", "B", "C", "D"};
    // TODO: Rename and change types of parameters

    public QuestionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        //modifify text for question view
        return view;
    }
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
    public static void generateQuestions(String topic, String difficulty) throws JSONException {
        JSONArray all =  questionBank.getJSONObject(topic).getJSONArray(difficulty);
        int[] ranNum = new int[5];
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
    }


    public static void loadNextQuestion() throws JSONException {
        currentQuestionCount+=1;
        currentQuestion = list[currentQuestionCount].getString("question");
        for (int i =0; i<4; i++){
            answers[i] = list[currentQuestionCount].getJSONArray("answers").get(i).toString();
        }
        correctAnsPos = list[currentQuestionCount].getInt("correct");

    }

    public static void showAnwer(){
        //show answer
    }

    public static boolean isCorrectAnswer(String answerNum){
        return answerNum == String.valueOf(correctAnsPos);
    }
}