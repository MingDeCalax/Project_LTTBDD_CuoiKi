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
    FrameLayout result;
    Button shareBtn;
    Drawable drawable;
    Bitmap bitmap;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResultFragment() {
        // Required empty public constructor
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
        shareBtn = view.findViewById(R.id.share_button);
        shareBtn.setOnClickListener(v ->  {
            File file = saveImage();
            if (file!=null)
                share(file);
        });
        return view;

    }



    private void share(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(this, getPackageName()+".provider",file);
        } else {
            uri = Uri.fromFile(file);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            shareBtn.performClick();
        } else
            Toast.makeText(this,"Permission Denied!", Toast.LENGTH_LONG).show();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private File saveImage() {
        if(!CheckPermission())
            return null;
        try {
            String path = Environment.getExternalStorageDirectory().toString() + "/AppName";
            File fileDir = new File(path);
            if (!fileDir.exists())
                fileDir.mkdir();

            String mPath = path+"QuizzieScore_"+new Date().getTime()+".png";
            Bitmap bitmap = screenShot();
            File file = new File(mPath);
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fOut);
            fOut.flush();
            fOut.close();

            Toast.makeText(this,"Your Result is saved successfully", Toast.LENGTH_LONG).show();
            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap screenShot() {
        View v = findViewById(R.id.rootview);
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(),v.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);

        return bitmap;
    }

    private boolean CheckPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return false;
        }
        return true;
    }
}