package com.example.quizzie_final;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
    public static TextView trueAns;
    public static Button nextButton;
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
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        TextView answerA = (TextView) view.findViewById(R.id.a_button);
        TextView answerB = (TextView) view.findViewById(R.id.b_button);
        TextView answerC = (TextView) view.findViewById(R.id.c_button);
        TextView answerD = (TextView) view.findViewById(R.id.d_button);
        answerA.setText("A. "  + answers[0]);
        answerB.setText("B. "  + answers[1]);
        answerC.setText("C. "  + answers[2]);
        answerD.setText("D. "  + answers[3]);
        progressBar.setProgress(currentQuestionCount);
        TextView ques = (TextView) view.findViewById(R.id.question_title);
        ques.setText("Câu hỏi " + currentQuestionCount + ":\n" + currentQuestion);
        int id[] = {R.id.a_button, R.id.b_button, R.id.c_button, R.id.d_button};
        trueAns = (TextView) view.findViewById(id[correctAnsPos-1]);
        nextButton = (Button) view.findViewById(R.id.next_button);
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
    public static void generateQuestions(MainActivity activity, String topic, String difficulty) throws JSONException {
        loadJSONFromAsset(activity);
        JSONArray all =  questionBank.getJSONObject(topic).getJSONArray(difficulty);
        currentQuestionCorrects = 0;
        currentQuestionCount = 0;
        int[] ranNum = new int[5];
        // generate 4 unique random numbers
        for (int i =0; i<5; i++){
            boolean b = true;
            while (b){
                ranNum[i] = rand.nextInt(all.length());
                for (int j=0; j<i; j++){
                    if (ranNum[j] == ranNum[i]){
                        b = true;
                        break;
                    }
                    if (j == i-1){
                        b = false;
                    }
                }
                if (i==0){
                    break;
                }
            }
            list[i] = all.getJSONObject(ranNum[i]);
        }
    }


    public static void loadNextQuestion() throws JSONException {
        currentQuestionCount+=1;
        currentQuestion = list[currentQuestionCount-1].getString("question");
        JSONArray ans = list[currentQuestionCount-1].getJSONArray("answers");
        for (int i =0; i<4; i++){
            answers[i] = ans.getString(i);
        }
        correctAnsPos = list[currentQuestionCount-1].getInt("correct");
    }

    public static void showAnwer(){
        //show answer
    }

    public static boolean isCorrectAnswer(String answerNum){
        return answerNum.equals(String.valueOf(correctAnsPos));
    }
}