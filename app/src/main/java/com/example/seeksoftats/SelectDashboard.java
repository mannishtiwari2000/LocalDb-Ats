package com.example.seeksoftats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SelectDashboard extends AppCompatActivity {
    Button btn_SelectDashboardActivity_AssetTagging, btn_SelectDashboardActivity_Locationtagging, btn_SelectDashboardActivity_Home, Btn_SelectDashboard_AssetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dashboard);
        btn_SelectDashboardActivity_AssetTagging = findViewById(R.id.Btn_selectDashboard_assetTagging);
        btn_SelectDashboardActivity_Locationtagging = findViewById(R.id.Btn_selectDashboard_LocationTagging);
        btn_SelectDashboardActivity_Home = findViewById(R.id.Btn_selectDashboard_Home);
        Btn_SelectDashboard_AssetLocation = findViewById(R.id.Btn_selectDashboard_AssetLocation);

//        Btn_SelectDashboard_AssetLocation.setOnClickListener(view -> startActivity(new Intent(SelectDashboard.this, AssetLocation.class)));
        btn_SelectDashboardActivity_Home.setOnClickListener(view -> startActivity(new Intent(SelectDashboard.this, Dashboard.class)));
//        btn_SelectDashboardActivity_Locationtagging.setOnClickListener(view -> startActivity(new Intent(SelectDashboard.this, LocationTrackingActivity.class)));
        btn_SelectDashboardActivity_AssetTagging.setOnClickListener(view -> startActivity(new Intent(SelectDashboard.this, TaggingAsset.class)));

    }
}