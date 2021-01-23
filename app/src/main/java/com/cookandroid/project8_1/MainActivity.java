package com.cookandroid.project8_1;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button btnRead, btnWrite;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            final View toastView = View.inflate(getApplicationContext(), R.layout.toast, null);
            //토스트 정의
            final Toast toast = new Toast(MainActivity.this);
            TextView toastText;

            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fileOutputStream = openFileOutput("file.txt", Context.MODE_PRIVATE);
                    String str = "쿡북 안드로이드";
                    fileOutputStream.write(str.getBytes()); //getBytes메소드를 사용하면 Byte배열로 변환 가능
                    fileOutputStream.close();   //다 하고나면 닫아주기
                    toast.setView(toastView);
                    toastText = toastView.findViewById(R.id.toastText);
                    toastText.setText("file.txt가 생성되었습니다");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {

            final Toast toast = new Toast(getApplicationContext());
            final View toastView = View.inflate(getApplicationContext(), R.layout.toast, null);
            final TextView toastText = toastView.findViewById(R.id.toastText);

            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileInputStream = openFileInput("file.txt");
                    byte[] txt = new byte[30];
                    int read = fileInputStream.read(txt);
                    Log.e("read", String.valueOf(read));
                    String str = new String(txt);   //읽어온 바이트 배열로 텍스트 만들기

                    toast.setView(toastView);
                    toastText.setText(str);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}