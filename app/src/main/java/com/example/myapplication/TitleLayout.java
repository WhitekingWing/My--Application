package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class TitleLayout extends LinearLayout{
    private DrawerLayout mDraw;
    private NavigationView navView;//导航视图
    public TitleLayout(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        LayoutInflater.from(context).inflate(R.layout.self_title,this);
        mDraw = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.imageButtonMenu).setOnClickListener(v -> {
            //打开滑动菜单  左侧出现
            mDraw.openDrawer(GravityCompat.START);
        });
        navView = findViewById(R.id.nav_view);
        //导航菜单点击
        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_books:
                    //关闭滑动菜单
                    mDraw.closeDrawer(GravityCompat.START);
                    break;
                case R.id.item_search:
                    mDraw.closeDrawer(GravityCompat.START);
                    Toast.makeText(context,"请点击右上角搜索按钮",Toast.LENGTH_LONG).show();
                    break;
                case R.id.item_label:
                    break;
                case R.id.item_create:
                    break;
                case R.id.item_setting:
                    break;
                case R.id.item_about:
                    break;
                default:
                    break;
            }
            return true;
        });
    }


}
