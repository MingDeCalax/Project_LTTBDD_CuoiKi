package com.example.quizzie_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryFragment extends Fragment {
    private final static SimpleDateFormat sdf = new SimpleDateFormat("'HH:mm  ' 'dd/MM/yyyy'");
    private static String time;
    private static String topic;
    private static int count = 0;
    View view;
    //time = sdf.format(new Date());
    public HistoryFragment() {
        // Required empty public constructor
    }


    public static void initHistory(String _topic){
        time = sdf.format(new Date());
        topic = _topic;
    }
    public static void increaseCount(){
        count+=1;
    }
    public static void addAndReset(LinearLayout historyList, View historyView){

        TextView numOfAnwers = historyView.findViewById(R.id.right_answers);
        TextView topicField = historyView.findViewById(R.id.topic);
        TextView timeField = historyView.findViewById(R.id.time);
        numOfAnwers.setText(count + "/5");
        topicField.setText(topic);
        timeField.setText(time);
        historyList.addView(historyView);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.history_fragment, container, false);
        return view;
    }
}