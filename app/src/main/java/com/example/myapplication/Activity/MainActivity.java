package com.example.myapplication.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    boolean islogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences("pre",0);
        editor=preferences.edit();
        islogin= preferences.getBoolean("islogin",false);
        if (networkconnected())
        {
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (islogin)
                    {
                        Intent intent = new Intent(MainActivity.this, Ecommerce.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(MainActivity.this, Login_activity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            handler.postDelayed(runnable,5000);


        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Alert...");
            builder.setMessage("Please Start Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    private boolean networkconnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}