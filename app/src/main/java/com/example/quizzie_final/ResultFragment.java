package com.example.quizzie_final;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String difficulty;
    public static String topic;
    public static int correct;
    public static String message;
    public ResultFragment() {
        // Required empty public constructor
    }
    public static void setDifficulty(String _difficulty){
        difficulty = _difficulty;
    }
    public static void setTopic(String _topic){
        topic = _topic;
    }
    public static void setCorrect(int _correct){
        correct = _correct;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_result, container, false);
        TextView topicdiff = view.findViewById(R.id.topic_diff);
        TextView result = view.findViewById(R.id.your_result);
        TextView mess = view.findViewById(R.id.mess);
        topicdiff.setText(topic + "    " + difficulty);
        result.setText(correct + "/5");
        message = "Tôi đã trả lời đúng " + correct + " trên 5 câu hỏi chủ đề "
                + topic + " với độ khó " + difficulty.toLowerCase() + " cùng tham gia chơi Quizz ngay nào!!!";
        mess.setText(message);
        return view;
    }

}