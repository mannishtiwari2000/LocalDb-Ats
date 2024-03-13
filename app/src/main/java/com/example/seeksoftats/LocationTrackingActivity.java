package com.example.seeksoftats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocationTrackingActivity extends AppCompatActivity implements View.OnClickListener{
    EditText lbl_LocationTracking_Rfid;
    Button btn_Location_Locate, btn_Location_Cancel;
    Spinner Spinner_Location_tracking;
    Context context;
    String epc, Selected_Location = null;
    List<String> listLocation;
    DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss a");
    String date = df.format(Calendar.getInstance().getTime());
    ProgressDialog dialog;
    ImageView backImg,LogoutImg;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tracking);
        lbl_LocationTracking_Rfid = findViewById(R.id.txt_LocationTracking_RfidNo);
        btn_Location_Cancel = findViewById(R.id.Btn_cancel_LocationTag);
        btn_Location_Locate = findViewById(R.id.Btn_Update_LocationTag);
        Spinner_Location_tracking = findViewById(R.id.spinner_Location_tracking);
        backImg=findViewById(R.id.Img_Back_LocationTracking);
        LogoutImg=findViewById(R.id.Img_Logout_LocationTracking);
        context = LocationTrackingActivity.this;
        btn_Location_Locate.setOnClickListener(this);
        btn_Location_Cancel.setOnClickListener(this);
        backImg.setOnClickListener(this);
        LogoutImg.setOnClickListener(this);
        listLocation = new ArrayList<>();
        btn_Location_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lbl_LocationTracking_Rfid.setText("");
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Btn_cancel_LocationTag:
            case R.id.Img_Back_LocationTracking:
                startActivity(new Intent(this,SelectDashboard.class));
                finish();
                break;
            case R.id.Btn_Update_LocationTag:
                try {

                    if (epc.length() == 0 || Selected_Location == null) {
                        Toast.makeText(context, "Please check Data...", Toast.LENGTH_SHORT).show();
                    } else {

                            SharedPreferences myPrefs =getSharedPreferences("Login_Details",
                                    MODE_PRIVATE);
                            String username = myPrefs.getString("username","");
                            dialog.setCancelable(false);
                            dialog.setMessage("Submitting...");
                            dialog.show();
//                            SubmitApi(epc, username,  date, "3", Selected_Location);


                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(context, "Some Data is missing", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Img_Logout_LocationTracking:
                Toast.makeText(context, "Logout...", Toast.LENGTH_SHORT).show();
                break;
        }

    }
//

}