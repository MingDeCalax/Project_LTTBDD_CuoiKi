package com.example.quizzie_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedList;

public class HistoryFragment extends Fragment {
    public static class History {
        public String time = "MM:HH DD/MM/YY";
        public int count = 0;
        public String difficulty = "Empty";
        public String topic = "Empty";
        public History() {
            //empty
        }

    }


        private final static SimpleDateFormat sdf = new SimpleDateFormat("'HH:mm  ' 'dd/MM/yyyy'");
        public static History current =  new History();
        public static ArrayList<History> listHistory =  new ArrayList<History>();

        public static void initTime(){
            current.time = sdf.format(new Date());
        }

        public static void addAndReset(){
            listHistory.add(current);
            current = new History();
        }

        public static void increaseCount(){
            current.count+=1;
        }

        public static void setDifficulty(String _difficulty){
            current.difficulty = _difficulty;
        }
        public static void setTopic(String _topic){
            current.topic = _topic;
        }
        public static String getDifficulty(){
            return current.difficulty;
        }
        public static String getTopic(){
            return current.topic;
        }


    public HistoryFragment() {
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
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        LinearLayout list = (LinearLayout) view.findViewById(R.id.history_list);
        for (History history : listHistory) {
            addHistory(list, history);
        }
        return view;
    }
    // add a view in linear layout
    private void addHistory(LinearLayout list, History history){
        View historyView = getLayoutInflater().inflate(R.layout.history_view, null);
        TextView corrects = historyView.findViewById(R.id.corrects);
        TextView topic_difficulty = historyView.findViewById(R.id.topic_difficulty);
        TextView time = historyView.findViewById(R.id.time);
        corrects.setText(history.count + "/5");
        topic_difficulty.setText(history.count + "/5");
        time.setText(history.time);
        list.addView(historyView);
    }

}