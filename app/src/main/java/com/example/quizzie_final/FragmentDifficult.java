package com.example.quizzie_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDifficult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDifficult extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    private static String topic;
    public static void setTopic(String _topic){
        topic = _topic;
    }
    public FragmentDifficult() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentDifficult newInstance(String _difficulty) {
        FragmentDifficult fragment = new FragmentDifficult();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_difficult, container, false);
        TextView guildTitle =  view.findViewById(R.id.guide_title);
        guildTitle.setText("Chủ đề " + topic + ".\n Hãy chọn độ khó");
        return view;
    }
}