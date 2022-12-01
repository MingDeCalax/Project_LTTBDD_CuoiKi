package com.example.quizzie_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Result extends AppCompatActivity {

    FrameLayout result;
    Button shareBtn;
    Drawable drawable;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        shareBtn = findViewById(R.id.share_button);
        shareBtn.setOnClickListener(view ->  {
                File file = saveImage();
                if (file!=null)
                    share(file);
        });
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
