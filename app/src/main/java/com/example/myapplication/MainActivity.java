package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView_HelloWorld;
    Button button_Chinese;
    Button button_English;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_HelloWorld = findViewById(R.id.textView);
        button_Chinese = findViewById(R.id.button);
        button_English = findViewById(R.id.button_english);
//        androidx.constraintlayout.widget.ConstraintLayout layout = findViewById(R.id.button_chinese);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView_HelloWorld.setText("你好世界！");
//            }
//        });
//        button_Chinese.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView_HelloWorld.setText("你好，世界！");
//                //textView_HelloWorld.setText(R.string.string_chinese);
//            }
//        });
//        button_English.setOnClickListener(new EnglishClickListener());
        button_Chinese.setOnClickListener(new ButtonClickListener());
        button_English.setOnClickListener(new ButtonClickListener());
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view == button_English) {
                textView_HelloWorld.setText("Hello World!");
                Log.v("test11",((Button)view).getText().toString());
            }
            else{
                textView_HelloWorld.setText("你好，世界！");
                Log.v("test232",((Button)view).getText().toString());
            }
        }
    }
}