package com.example.quizzie_final;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizzie_final.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}