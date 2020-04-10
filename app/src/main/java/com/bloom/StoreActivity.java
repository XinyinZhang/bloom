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
    private ImageView iv_lily;
    private ImageView iv_rose;
    private SharedPreferences.Editor editor;

    private SharedPreferences c_Prefs;
    private SharedPreferences l_Prefs;
    private int c_num;
    private int f_num;
    private Boolean is_lily_locked;
    private Boolean is_rose_locked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        c_Prefs = getSharedPreferences("prefID", MODE_PRIVATE);

        f_num = Integer.parseInt(c_Prefs.getString("dead_flower",null));
        c_num = c_Prefs.getInt("coinNum", 0);

        l_Prefs = getSharedPreferences("locked_item", MODE_PRIVATE);
        is_lily_locked = l_Prefs.getBoolean("locked_lily",true); //locked by default
        is_rose_locked = l_Prefs.getBoolean("locked_rose", true);



        iv_potion = findViewById(R.id.icon_potion);
        iv_lily = findViewById(R.id.icon_lily);
        iv_rose = findViewById(R.id.icon_rose);
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

        iv_lily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //check if coinNum >= 80 and if lily is already unlocked
                AlertDialog.Builder dialog = new AlertDialog.Builder(StoreActivity.this);
                dialog.setMessage("Do you want to buy a lily")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (c_num < 80){
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
                                else if (!is_lily_locked){ //lily is already unlocked
                                    AlertDialog.Builder u_dialog = new AlertDialog.Builder(StoreActivity.this);
                                    u_dialog.setMessage("The lily is already unlocked.")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });
                                    AlertDialog u_alert = u_dialog.create();
                                    u_alert.show();
                                }

                                else { //unlock lily, coin - 80
                                    editor = l_Prefs.edit();
                                    editor.putBoolean("locked_lily", false);
                                    editor.apply();

                                    editor = c_Prefs.edit();
                                    c_num = c_num - 80;
                                    editor.putInt("coinNum", c_num);
                                    editor.apply();
                                    //a dialog box to indicate purchase succeed
                                    AlertDialog.Builder s_dialog = new AlertDialog.Builder(StoreActivity.this);
                                    s_dialog.setTitle("Purchase succeed")
                                            .setMessage("You have unlocked the lily. You now have "+c_num+" coins left.")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });
                                    AlertDialog s_alert = s_dialog.create();
                                    s_alert.show();
                                }
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.show();

            }
        });
        }

}
