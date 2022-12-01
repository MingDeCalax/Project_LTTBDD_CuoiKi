package com.example.quizzie_final;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import java.util.List;

public class HistoryFragment extends Fragment {


        private final static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm   dd/MM/yyyy");
        public static History current =  new History();
        public static ArrayList<History> history_list = new ArrayList<History>();

        public static void initTime(){
            current.time = sdf.format(new Date());
        }

        public void addAndReset(MainActivity activity) {
            history_list.add(current);
            current = new History();
        }

        public static void increaseCount(){
            current.count = current.count+1;
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
        public static int getCorrects(){
            return current.count;
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
        LinearLayout layout = view.findViewById(R.id.history_list);
        for (History history : history_list){
            addHistory(getActivity(), history, layout);
        }
        return view;
    }




    // add a view in linear layout
    public void addHistory(Activity activity, History history, LinearLayout layout){
        View historyView = LayoutInflater.from(activity).inflate(R.layout.history_view, null);
        if(historyView != null) {
        }
        TextView corrects =  historyView.findViewById(R.id.corrects_indicator);
        TextView topic_difficulty = historyView.findViewById(R.id.topic_difficulty);
        TextView time =  historyView.findViewById(R.id.time_indicator);
        corrects.setText(String.valueOf(history.count) + "/5");
        topic_difficulty.setText(history.topic + "\n" + history.difficulty);
        time.setText(history.time);
        layout.addView(historyView);

    }

}