package com.example.seeksoftats;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rscja.barcode.BarcodeDecoder;
import com.rscja.barcode.BarcodeFactory;
import com.rscja.deviceapi.entity.BarcodeEntity;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TaggingAsset extends AppCompatActivity implements View.OnClickListener {

    boolean QrRead, RfidRead;
    Context context = this;
    EditText edt_AssetID_Tagging, edt_rfidId_No_Tagging;
    BarcodeDecoder barcodeDecoder = BarcodeFactory.getInstance().getBarcodeDecoder();
    String epc, AssetID;
    Button Btn_AssetSubmitTag_Tagging, Btn_Assetcancel_Tagging, Btn_ReAssign_tagging, Btn_Cancel_tagging;
    ProgressDialog progressDialog;
    //    LocalDataBase dataBase;
    @SuppressLint("SimpleDateFormat")
    DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss a");
    String date = df.format(Calendar.getInstance().getTime());
    String Username, AssetIdentifyNo, username;
    SharedPreferences sharedPreferences;
    ImageView Logout, Back;
    LocalDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging_asset);
        dataBase = new LocalDataBase(this);
        Back = findViewById(R.id.Back_TaggingAsset);
        Logout = findViewById(R.id.Logout_TaggingAsset);

        edt_AssetID_Tagging = findViewById(R.id.edt_assetId_tagging);
        Btn_ReAssign_tagging = findViewById(R.id.Btn_ReAssign_Tagging);
        edt_rfidId_No_Tagging = findViewById(R.id.edt_assetRfido_taggingset);
        Btn_AssetSubmitTag_Tagging = findViewById(R.id.Btn_TagAsset_Tagging);
        Btn_Assetcancel_Tagging = findViewById(R.id.Btn_Cancel_Tagging);
        progressDialog = new ProgressDialog(context);
//        dataBase = new LocalDataBase(context);
        edt_AssetID_Tagging.setOnClickListener(this);
        edt_rfidId_No_Tagging.setOnClickListener(this);
        Btn_AssetSubmitTag_Tagging.setOnClickListener(this);
        Btn_Assetcancel_Tagging.setOnClickListener(this);
        Btn_ReAssign_tagging.setOnClickListener(this);
        Back = findViewById(R.id.Back_TaggingAsset);
        Logout = findViewById(R.id.Logout_TaggingAsset);
        Btn_Cancel_tagging = findViewById(R.id.Btn_Cancel_Tagging);
        sharedPreferences = getSharedPreferences("Login_Details", 0);
        username = sharedPreferences.getString("userId", "");
        new InitTask().execute();
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

        edt_AssetID_Tagging.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edt_AssetID_Tagging.getText().toString().length() > 0) {
                    edt_AssetID_Tagging.clearFocus();
                    edt_rfidId_No_Tagging.requestFocus();
                    edt_rfidId_No_Tagging.setCursorVisible(true);
                }
            }
        });

        edt_AssetID_Tagging.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                QrRead = true;
                RfidRead = false;
//                if (AssetID_txt.getText().toString().length()>0) {
//                    AssetID_txt.clearFocus();
//                    Rfid_txt.requestFocus();
//                    Rfid_txt.setCursorVisible(true);
//                }

            }
        });

        edt_rfidId_No_Tagging.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                RfidRead = true;
                QrRead = false;
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaggingAsset.this, SelectDashboard.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_assetId_tagging:

                break;
            case R.id.edt_assetRfido_taggingset:
//                ReadSingleTag();
                break;

            case R.id.Btn_TagAsset_Tagging:
//

                AssetIdentifyNo = edt_AssetID_Tagging.getText().toString();
                try {
                    if (epc.length() == 0 || AssetIdentifyNo.length() == 0) {
                        Toast.makeText(context, "Please check Data...", Toast.LENGTH_SHORT).show();
                    } else {

//
                        boolean status = dataBase.AddTagging(epc, AssetIdentifyNo);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Tagging...");
                        progressDialog.show();
                        if (status) {
                            Toast.makeText(context, "Tagging Done....", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Failed to Tag...", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Some Data is missing", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.Btn_Cancel_Tagging:
                edt_AssetID_Tagging.setText("");
                edt_rfidId_No_Tagging.setText("");
                break;
            case R.id.Btn_ReAssign_Tagging:
                AssetIdentifyNo = edt_AssetID_Tagging.getText().toString();
                if (epc.length() == 0 || AssetIdentifyNo.length() == 0 || username.length() == 0) {
                    Toast.makeText(context, "Please check Data...", Toast.LENGTH_SHORT).show();
                } else {
                    //Method For Taggging
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Tagging...");
                    progressDialog.show();

                }
        }

    }

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            open();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(TaggingAsset.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }

    private void start() {
        barcodeDecoder.startScan();
    }

    private void stop() {
        barcodeDecoder.stopScan();
    }

    private void open() {
        barcodeDecoder.open(this);
           /* todo success Sound
            BarcodeUtility.getInstance().enablePlaySuccessSound(this,true);
            */
        barcodeDecoder.setDecodeCallback(new BarcodeDecoder.DecodeCallback() {
            @Override
            public void onDecodeComplete(BarcodeEntity barcodeEntity) {
                if (barcodeEntity.getResultCode() == BarcodeDecoder.DECODE_SUCCESS) {
//                    tvData.setText("data:"+barcodeEntity.getBarcodeData());
                    AssetID = null;
                    edt_AssetID_Tagging.getText().clear();
                    AssetID = barcodeEntity.getBarcodeData();
                    edt_AssetID_Tagging.setText(AssetID);
                }
            }
        });
        barcodeDecoder.stopScan();
    }

    private void close() {
        barcodeDecoder.close();
    }

    public void Logout() {

        SharedPreferences myPrefs = getSharedPreferences("Login_Details",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(TaggingAsset.this,
                Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Logout...", Toast.LENGTH_SHORT).show();
    }
}