package com.bloom;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StoreActivity extends AppCompatActivity {
    private ImageView iv_potion;
    SharedPreferences.Editor editor;
    SharedPreferences c_Prefs;
    int c_num;
    int f_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        c_Prefs = getSharedPreferences("prefID", MODE_PRIVATE);

        f_num = Integer.parseInt(c_Prefs.getString("dead_flower",null));
        c_num = c_Prefs.getInt("coinNum", 0);

        iv_potion = findViewById(R.id.icon_potion);
        iv_potion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //1. check if has 50 coins, 2. check if there is a dead flower
                                            // process purchase: deadflower - 1, coinNum - 50
                AlertDialog.Builder dialog = new AlertDialog.Builder(StoreActivity.this);
                dialog.setTitle("The magic potion can revive a dead flower")
                      .setMessage("Do you want to buy a magic potion")
                      .setNegativeButton("No", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {

                          }
                      })
                      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {

                              if (f_num <= 0){ //check flower num
                                  AlertDialog.Builder c_dialog = new AlertDialog.Builder(StoreActivity.this);
                                  c_dialog.setMessage("You don't have any dead flower in your garden")
                                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {

                                              }
                                          });
                                  AlertDialog c_alert = c_dialog.create();
                                  c_alert.show();
                              }
                              else if(c_num < 50) { //check coin num
                                    AlertDialog.Builder c_dialog = new AlertDialog.Builder(StoreActivity.this);
                                    c_dialog.setMessage("You don't have enough coins")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });
                                    AlertDialog c_alert = c_dialog.create();
                                    c_alert.show();
                              }

                           else { //process purchase: coinNum - 50, dead flower - 1
                                  editor = c_Prefs.edit();
                                  f_num -= 1;
                                  editor.putString("dead_flower", Integer.toString(f_num));
                                  editor.apply();
                                  c_num = c_num - 50;
                                  editor.putInt("coinNum", c_num);
                                  editor.apply();
                                  //generate a dialog box to show purchase succeed

                                  AlertDialog.Builder c_dialog = new AlertDialog.Builder(StoreActivity.this);
                                  c_dialog.setTitle("Purchase succeed")
                                          .setMessage("You have revived a dead flower in your garden. You now have "+c_num+" coins and "+f_num+" dead flowers.")
                                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {

                                              }
                                          });
                                  AlertDialog c_alert = c_dialog.create();
                                  c_alert.show();
                           }

                          }
                      });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });
        }

}
