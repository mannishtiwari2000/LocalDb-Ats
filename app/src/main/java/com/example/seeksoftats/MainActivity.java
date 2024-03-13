package com.example.seeksoftats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String baseURLStr = "http://192.168.1.7/seeksoft/api/";
    String MacAddress;
    public static String ComanyCode = "1200", PlantCode = "1", ClientCode = "null", Ipaddress = null;
    SharedPreferences sharedPreferences;
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNext = findViewById(R.id.btnNext);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        ClientCode = sharedPreferences.getString("Client code", " ");
        MacAddress = getAndroidId();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginForm.class);
                startActivity(intent);
                finish();

            }

        });


        if (ClientCode.matches(" ")) {
            ShowDailog();
        }
    }

    private void ShowDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dailogbox = LayoutInflater.from(this).inflate(R.layout.customdialog, null);
        EditText ClientCodeval = dailogbox.findViewById(R.id.ClientCODE);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (ClientCodeval.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Client Code...", Toast.LENGTH_SHORT).show();
                } else {
                    saveDataToSharedPreferences(ClientCodeval.getText().toString());
                    dialogInterface.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setView(dailogbox);
        builder.setCancelable(false);
        builder.show();
    }


    private String getAndroidId() {
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    private void saveDataToSharedPreferences(String data) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Client code", data);
        editor.apply();
    }

}
