package com.example.quizzie_final;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.quizzie_final.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment currentFrag ;
    // remember this
    private Fragment mainFrag;
    public HistoryFragment historyFrag = new HistoryFragment();
    private BottomNavigationView bottomNavi;
    private boolean firstTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavi = findViewById(R.id.bottomNavigationView);
        mainFrag = new TopicsFragment();
        replaceFragment(mainFrag);
        NavigationBarView.OnItemSelectedListener naviListener  = item -> {
            switch (item.getItemId()) {
                case R.id.game:
                    if (currentFrag != mainFrag){
                        replaceFragment(mainFrag);
                    }
                    return true;
                case R.id.history:
                    if(currentFrag != historyFrag) {
                        replaceFragment(historyFrag);
                    }
                    return true;
            }
            return false;
        };
        bottomNavi.setOnItemSelectedListener(naviListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.gop_y:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "20021517@vnu.edu.vn" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Góp ý trò chơi (Android SDK: " + Build.VERSION.SDK_INT + "; version " + Build.VERSION.RELEASE +")");
                intent.putExtra(Intent.EXTRA_TEXT, "Góp ý của bạn: \n");
                intent.setType("message/rfc822");

                if (intent.resolveActivity(getPackageManager())!=null){
                    startActivity(Intent.createChooser(intent, "Select Email Client"));
                } else {
                    Toast.makeText(MainActivity.this, "No Mail App is installed!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.info:
                replaceFragment(new InfoFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.content_frame, fragment, null);
        fragmentTransaction.commit();
        currentFrag = fragment;
    }
     void switchFragment(Fragment newFragment){
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         fragmentTransaction.setReorderingAllowed(true);
         fragmentTransaction.detach(currentFrag);
         fragmentTransaction.attach(newFragment);
         fragmentTransaction.commit();
         currentFrag = newFragment;
     }
     void stopAndAddFragment(Fragment newFragment){
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         fragmentTransaction.setReorderingAllowed(true);
         fragmentTransaction.detach(currentFrag);
         fragmentTransaction.add(newFragment, null);
         fragmentTransaction.commit();
         currentFrag = newFragment;
     }

    public void submitAnswer(View view){
        TextView selectedAnswer = (TextView) view;
        Log.v(view.getTag().toString(), "TAGA");
         if(QuestionFragment.isCorrectAnswer(view.getTag().toString())){
             QuestionFragment.currentQuestionCorrects+=1;
             HistoryFragment.increaseCount();
             selectedAnswer.setBackgroundResource(R.drawable.green_button);
         }
         else{
             selectedAnswer.setBackgroundResource(R.drawable.red_button);
             QuestionFragment.trueAns.setBackgroundResource(R.drawable.green_button);
         }
         QuestionFragment.nextButton.setVisibility(View.VISIBLE);
         QuestionFragment.nextButton.setClickable(true);

         //show answer
         // show next button
    }
    public void terminateQuestion(View view) throws JSONException {
        if (QuestionFragment.currentQuestionCount == 5){
            Log.v("CCC", "DM");
            historyFrag.addAndReset(this);
            mainFrag = new TopicsFragment();
            replaceFragment(mainFrag);

        }
        else {
            QuestionFragment.loadNextQuestion();
            mainFrag = new QuestionFragment();
            replaceFragment(mainFrag);
        }
            //create and show new fragment for new question
    }

    public void chooseTopic(View view) throws JSONException {
        switch (view.getId()) {
            case R.id.math_button:
                HistoryFragment.setTopic("math");
                break;
            case R.id.economics_button:
                HistoryFragment.setTopic("economics");
                break;
            case R.id.geography_button:
                HistoryFragment.setTopic("geography");
                break;
            case R.id.literature_button:
                HistoryFragment.setTopic("literature");
                break;
            case R.id.music_button:
                HistoryFragment.setTopic("music");
                break;
            case R.id.sports_button:
                HistoryFragment.setTopic("sports");
                break;
        }
        mainFrag = new FragmentDifficult();
        replaceFragment(mainFrag);
    }

    public void chooseDifficulty(View view) throws JSONException {
        switch (view.getId()) {
            case R.id.easy_button:
                HistoryFragment.setDifficulty("easy");
                break;
            case R.id.normal_button:
                HistoryFragment.setDifficulty("medium");
                break;
            case R.id.hard_button:
                HistoryFragment.setDifficulty("hard");
                break;
        }
        QuestionFragment.generateQuestions(this ,HistoryFragment.getTopic(), HistoryFragment.getDifficulty());
        HistoryFragment.initTime();
        QuestionFragment.loadNextQuestion();
        mainFrag = new QuestionFragment();
        replaceFragment(mainFrag);
        //set Fragment
    }


}