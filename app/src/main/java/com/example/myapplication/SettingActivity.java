package com.example.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        Spinner spinner = findViewById(R.id.spinner_theme);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String content = adapterView.getItemAtPosition(i).toString();
        if(content.equals("Dark"))
        {
            this.setTheme(R.style.Theme_SuperShopper);
            startActivity(new Intent(this,SettingActivity.class));
        }
        else if(content.equals("Light"))
        {
            this.setTheme(R.style.Theme_Bookshelf);
            startActivity(new Intent(this,SettingActivity.class));
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
