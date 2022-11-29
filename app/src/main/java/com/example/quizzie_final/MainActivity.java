package com.example.quizzie_final;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
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
    private Fragment currentFrag = new TopicsFragment();
    // remember this
    private Fragment mainFrag = new TopicsFragment();
    private HistoryFragment historyFrag = new HistoryFragment();
    private BottomNavigationView bottomNavi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(currentFrag);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottomNavi = findViewById(R.id.bottomNavigationView);
        NavigationBarView.OnItemSelectedListener naviListener  = item -> {
            switch (item.getItemId()) {
                case R.id.game:
                    if (currentFrag != mainFrag){
                        replaceFragment(mainFrag);
                    }
                    return true;
                case R.id.history:
                    if (currentFrag != historyFrag) {
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
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
        currentFrag = fragment;
    }

    public void submitAnswer(View view){
        if(QuestionFragment.isCorrectAnswer(view.getTag().toString())){
            QuestionFragment.currentQuestionCorrects+=1;
            HistoryFragment.increaseCount();
        };
        //show answer
        // show next button
    }
    public void terminateQuestion(View view) throws JSONException {
        if (QuestionFragment.currentQuestionCount == 5){
            // show result & play again menu
        }
        QuestionFragment.loadNextQuestion();
        //create and show new fragment for new question
    }

    public void chooseTopic(View view) throws JSONException{
        //HistoryFragment.setTopic()
        //Replace Fragment
    }

    public void chooseDifficulty(View view) throws JSONException {
        HistoryFragment.initTime();
        //HistoryFragment.setDifficulty(); get topic from view
        QuestionFragment.generateQuestions(HistoryFragment.getTopic(), HistoryFragment.getDifficulty());
        //set Fragment
    }


}