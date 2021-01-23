package com.cookandroid.project8_1;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    DatePicker datePicker;
    EditText editDiary;
    Button btnWrite;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setTitle("간단 일기장");

        datePicker = findViewById(R.id.datePicker);
        editDiary = findViewById(R.id.editDiary);
        btnWrite = findViewById(R.id.btnWrite);

        //현재 날짜를 구하고 데이트 피커 초기화하기
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Log.e("date ", year + "/" + month + "/" + day);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName = year + "_" + (monthOfYear + 1) + "_" + dayOfMonth + ".txt";
                Log.e("fileName", fileName);
                String str = readDiary(fileName);
                editDiary.setText(str);
                btnWrite.setEnabled(true);
            }
        });


        btnWrite.setOnClickListener(new View.OnClickListener() {
            Toast toast = new Toast(getApplicationContext());
            View toastView = View.inflate(getApplicationContext(), R.layout.toast, null);
            TextView toastText = toastView.findViewById(R.id.toastText);

            @Override
            public void onClick(View v) {
                try {
                    //아웃풋스트림 이용해서 일기파일을 쓰기모드로 열기
                    FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = editDiary.getText().toString();
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.close();

                    toast.setView(toastView);
                    toastText.setText(fileName + " 이 저장됨");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String readDiary(String fileName) {

        String diaryStr = null;
        try {
            //일기 파일을 열어서 입력스트림에 저장
            FileInputStream fileInputStream = openFileInput(fileName);
            //저장할 바이트 배열 생성
            byte[] txt = new byte[500];
            //인풋스트림을 이용해서 파일의 내용을 바이트배열에 저장
            int read = fileInputStream.read(txt);   //fileInputStream을 이용해서 fileName을 읽고 그 바이트배열들을 txt에 저장하고
                                                    //파일 길이를 반환한다.
            //스트림닫기
            fileInputStream.close();
            Log.e("fileLength", String.valueOf(read));
            //바이트배열로 된 파일 내용을 문자열에 저장
            diaryStr = new String(txt).trim();

            //버튼 이름을 수정하기로 변경
            btnWrite.setText("수정하기");
        } catch (IOException e) {
            //파일이 없을 시에
            editDiary.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }
        return diaryStr;
    }
}