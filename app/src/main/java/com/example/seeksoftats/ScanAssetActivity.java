package com.example.seeksoftats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.exception.ConfigurationException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ScanAssetActivity extends AppCompatActivity {
    private Context context = this;
    private String epc;
    boolean BtnStatus = false;
    TextView lbl_ScanAsset_SearialNo, lbl_ScanAsset_AssetDescription, lbl_ScanAsset_Quantity, lbl_ScanAsset_AssetClass,
            lbl_ScanAsset_CostCenter, lbl_ScanAsset_Plant, lbl_ScanAsset_Business, lbl_ScanAsset_Captialization,
            lbl_ScanAsset_AssetUid, lbl_ScanAsset_Status, lbl_ScanAsset_AuditOn, lbl_ScanAsset_LOC, lbl_ScanAsset_Rfid;
    ProgressDialog dialog;
    LocalDataBase dataBase;
    List<ModelClass> modelClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_asset);
        dialog = new ProgressDialog(this);
        modelClassList = new ArrayList<>();
        dataBase = new LocalDataBase(context);
        lbl_ScanAsset_SearialNo = findViewById(R.id.lbl_scanActivity_Serial_No);
        lbl_ScanAsset_AssetDescription = findViewById(R.id.lbl_scanActivity_Asset_Description);
        lbl_ScanAsset_Quantity = findViewById(R.id.lbl_scanActivity_Quantity);
        lbl_ScanAsset_AssetClass = findViewById(R.id.lbl_scanActivityAsset_Class);
        lbl_ScanAsset_CostCenter = findViewById(R.id.lbl_scanActivity_CostCenter);
        lbl_ScanAsset_Plant = findViewById(R.id.lbl_scanActivity_Plant);
        lbl_ScanAsset_Business = findViewById(R.id.lbl_scanActivity_Business);
        lbl_ScanAsset_Captialization = findViewById(R.id.lbl_scanActivity_Capitalization);
        lbl_ScanAsset_AssetUid = findViewById(R.id.lbl_scanActivity_AssetUId);
        lbl_ScanAsset_Status = findViewById(R.id.lbl_scanActivity_Status);
        lbl_ScanAsset_AuditOn = findViewById(R.id.lbl_scanActivity_Audit);
        lbl_ScanAsset_LOC = findViewById(R.id.lbl_scanActivity_LastLocation);
        lbl_ScanAsset_Rfid = findViewById(R.id.lbl_scanActivity_RfidNumber);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 280 || keyCode == 293)//keyCode=280
        {
            if (!BtnStatus) {

                String val = ReadSingleTag();

                if (val != null) {
                    dialog.setCancelable(false);
                    BtnStatus = true;
                    //Fetchunng
                    if (!(modelClassList.size() >0)){
                        modelClassList = dataBase.getAllAsseTDetails(val);
                    }else {
                        Toast.makeText(context, "Alread", Toast.LENGTH_SHORT).show();
                    }

                    SetData(modelClassList);
                    dialog.setMessage("Fetching Details...");
                    dialog.show();

                } else {
                    Toast.makeText(context, "Not Read...", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(context, "Wait...", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    private void SetData(List<ModelClass> modelClassList) {
        lbl_ScanAsset_Rfid.setText(modelClassList.get(0).getTagId());
        lbl_ScanAsset_LOC.setText(modelClassList.get(0).getLastSeenLoc());
        lbl_ScanAsset_Status.setText(modelClassList.get(0).getStatus());
        lbl_ScanAsset_AssetUid.setText(modelClassList.get(0).getAssetUID());
        lbl_ScanAsset_AssetDescription.setText(modelClassList.get(0).getAssetDesc());
        lbl_ScanAsset_Plant.setText(modelClassList.get(0).getPlant());
        lbl_ScanAsset_CostCenter.setText(modelClassList.get(0).getCostCenter());
        lbl_ScanAsset_AuditOn.setText(modelClassList.get(0).getLast_inv_on());
        lbl_ScanAsset_SearialNo.setText(modelClassList.get(0).getSerialNo());
        lbl_ScanAsset_Quantity.setText(modelClassList.get(0).getQty());
        lbl_ScanAsset_Business.setText(modelClassList.get(0).getBusinessArea());
        lbl_ScanAsset_Captialization.setText(modelClassList.get(0).getCapitaliationDate());

    }


    private String ReadSingleTag() {
        RFIDWithUHFUART rfidWithUHFUART;
        UHFTAGInfo uhftagInfo;
        try {
            rfidWithUHFUART = RFIDWithUHFUART.getInstance();
            rfidWithUHFUART.init(context);
            uhftagInfo = rfidWithUHFUART.inventorySingleTag();

            if (uhftagInfo != null) {
                String tid = uhftagInfo.getTid();
                epc = uhftagInfo.getEPC();
                Log.d("Rfid No : ", epc);
                String user = uhftagInfo.getUser();
                Toast.makeText(context, "" + epc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Not Read", Toast.LENGTH_SHORT).show();
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something Wrong...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed...", Toast.LENGTH_SHORT).show();
        }
        return epc;
    }

}