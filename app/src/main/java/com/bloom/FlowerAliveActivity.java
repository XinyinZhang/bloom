package com.bloom;

import android.content.Intent;
import android.os.Build;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FlowerAliveActivity extends AppCompatActivity {
    private navBarListener navBar;
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_alive);
        tv = findViewById(R.id.text2);
       SharedPreferences c_Prefs = getSharedPreferences("prefID", MODE_PRIVATE);
        int c_num = c_Prefs.getInt("coinNum", 0);
        tv.setText("      " + Integer.toString(c_num));

        iv = findViewById(R.id.icon_coin2);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlowerAliveActivity.this, StoreActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        SharedPreferences thePrefs = getSharedPreferences("tagpage", MODE_PRIVATE);

        String f_type = thePrefs.getString("flower_type",null);

        if (f_type == "f1") {
            RelativeLayout dd = findViewById(R.id.alive);
            dd.setBackgroundResource(R.drawable.sunflower_level3);
        } else if (f_type == "f2") {
            RelativeLayout dd = findViewById(R.id.alive);
            dd.setBackgroundResource(R.drawable.rose_level3);
        } else if (f_type == "f3") {
            RelativeLayout dd = findViewById(R.id.alive);
            dd.setBackgroundResource(R.drawable.lily_level3);
        } else {
            RelativeLayout dd = findViewById(R.id.alive);
            dd.setBackgroundResource(R.drawable.sunflower_level3);
        }

        //Change Color of StatusBar to match background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.skyBlueAlive));
        }

        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

    }
}
