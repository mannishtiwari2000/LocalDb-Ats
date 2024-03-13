package com.example.seeksoftats;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.exception.ConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Audit extends AppCompatActivity {
    Adapter_Audit adapter_audit;
    List<ModelClass> stringList;
    ListView listViewAudit;
    private boolean BtnStatus = false, ScanRead = true;
    private String epc;
    List<String> templist, rfidlist;
    RFIDWithUHFUART rfidWithUHFUART;
    private boolean loopFlag = false;
    //    Handler handler;
    TextView Found, notFound, Total, Miscellaneous;
    int total = 0;
    boolean scanstatus = true;
    private String ScanStart = "", ScanStop = "", auditdata = "", LocationAudit;
    String assetUID, location, costCenter;
    ProgressDialog dialog;
    int found = 0, MisLo = 0, notfound = 0;
    SimpleDateFormat SDFormat;
    Button btn_ReportSubmit_Audit, Btn_Reset_Audit, Btn_Back_Audit;
    ImageView Img_Back_Audit, Img_logout_Audit;
    int audi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);
        listViewAudit = findViewById(R.id.Listview_Missing);
        btn_ReportSubmit_Audit = findViewById(R.id.Btn_save_Audit);
        Img_Back_Audit = findViewById(R.id.Img_Back_Audit);
        Img_logout_Audit = findViewById(R.id.Img_Logout_Audit);
        try {
            rfidWithUHFUART = RFIDWithUHFUART.getInstance();
            rfidWithUHFUART.init(this);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        stringList = new ArrayList<>();
        templist = new ArrayList<>();
        stringList.clear();
        templist.clear();
        Btn_Back_Audit = findViewById(R.id.btn_back_Audit);
        Btn_Reset_Audit = findViewById(R.id.btn_reset_Audit);
        Found = findViewById(R.id.lbl_Audit_Found);
        notFound = findViewById(R.id.lbl_audit_NotFoundMissig);
        Total = findViewById(R.id.lbl_Missing_total);
        Miscellaneous = findViewById(R.id.lbl_audit_Miscell);

        Btn_Back_Audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Audit.this, Dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Clear();
                finish();
            }
        });
        rfidlist = new ArrayList<>();
        Btn_Reset_Audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
            }
        });

        Img_Back_Audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Audit.this, Dashboard.class));
                finish();
            }
        });

//        Img_logout_Audit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Logout();
//            }
//        });
        SDFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");

        dialog = new ProgressDialog(this);


        btn_ReportSubmit_Audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stringList.size() > 0) {
                    BatchLst();
                    dialog.setCancelable(false);
                    dialog.setMessage("Submitting....");
                    dialog.show();
                } else {
                    Toast.makeText(Audit.this, "No Data to Submit...", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//
//
//        if (keyCode == 280)//keyCode=280
//        {
//            if (!BtnStatus) {
//                if (stringList.size() > 0) {
//
//                    if (scanstatus) {
//                        scanstatus = false;
//
//                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
//                        String date = df.format(Calendar.getInstance().getTime());
//
//                        ScanStart = String.valueOf(date);
//                        System.out.println("Scan Start " + ScanStart);
//                        MulitpleRead();
//                        Toast.makeText(this, "start....", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "stop...", Toast.LENGTH_SHORT).show();
//                        rfidWithUHFUART.stopInventory();
//                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
//                        String date = df.format(Calendar.getInstance().getTime());
//                        ScanStop = String.valueOf(date);
//                        System.out.println("Scan Stop " + ScanStart);
//                        scanstatus = true;
//                    }
//
//                } else {
//                    String val = ReadSingleTag();
//                    try {
//                        if (val != null) {
//                            dialog.setCancelable(false);
//                            BtnStatus = true;
//                            dialog.setMessage("Fetching Details...");
//                            dialog.show();
//                            SubmitApi(val);
//                        } else {
//                            Toast.makeText(this, "Not Read...", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Wait...", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        return super.onKeyUp(keyCode, event);
//
//    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 280 || keyCode == 293)//keyCode=280
        {
            if (BtnStatus) {
                if (!scanstatus) {

                    Toast.makeText(this, "stop...", Toast.LENGTH_SHORT).show();
                    rfidWithUHFUART.stopInventory();
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
                    String date = df.format(Calendar.getInstance().getTime());
                    ScanStop = date;
                    ScanRead = true;
                    scanstatus = true;

                    BtnStatus = false;
                }

            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 280 || keyCode == 293) {
            if (stringList.size() > 0) {
                if (!BtnStatus) {
                    BtnStatus = true;
                    if (scanstatus) {

                        scanstatus = false;
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
                        String date = df.format(Calendar.getInstance().getTime());
                        ScanStart = date;
                        auditdata = date.replace("/", "").replace(":", "").replace(" ", "").replaceAll("[^0-9]", "");
                        ;
                        if (ScanRead) {
//                            MulitpleRead();
                            rfidWithUHFUART.startInventoryTag();
                            loopFlag = true;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    UHFTAGInfo uhftagInfo;
                                    Message msg;
                                    while (loopFlag) {
                                        uhftagInfo = rfidWithUHFUART.readTagFromBuffer();
                                        if (uhftagInfo != null) {
                                            msg = handler.obtainMessage();
                                            msg.obj = uhftagInfo;
                                            handler.sendMessage(msg);

                                        }
                                    }
                                }
                            }).start();

                            ScanRead = false;
                        }
                        Toast.makeText(this, "start....", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                String val = ReadSingleTag();
//                try {
//                    if (val != null) {
//                        dialog.setCancelable(false);
//                        BtnStatus = true;
//                        dialog.setMessage("Fetching Audit List...");
//                        dialog.show();
////                        SubmitApi(val);
//                    } else {
//                        Toast.makeText(this, "Not Read...", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        } else if (keyCode == 4) {
            Intent intent = new Intent(Audit.this, Dashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Clear();
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }

    private String ReadSingleTag() {

        UHFTAGInfo uhftagInfo;

        uhftagInfo = rfidWithUHFUART.inventorySingleTag();
        if (uhftagInfo != null) {
            String tid = uhftagInfo.getTid();
            epc = uhftagInfo.getEPC();
            Log.d("Rfid No : ", epc);
            String user = uhftagInfo.getUser();
            Toast.makeText(this, "" + epc, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Read", Toast.LENGTH_SHORT).show();
        }
        return epc;
    }

//
//    private void SubmitApi(String Rfidnumber) throws JSONException {
////        String url = " http://belapi.netpaze.com/api/getlistforaudit/".concat(Rfidnumber);
//        String url = WelcomeScreenActivity.baseURLStr.concat("getlistforaudit/").concat(Rfidnumber);
//        JSONObject obj = new JSONObject();
//
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        final String requestBody = obj.toString();
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
//
//            try {
//
//                BtnStatus = false;
//                JSONObject object1 = new JSONObject(response);
//                JSONArray array = (JSONArray) object1.getJSONArray("aList");
//
//                String Status = object1.getString("status");
//                if (Status.matches("1")) {
//                    LocationAudit = object1.getString("message");
//                } else {
//                    Toast.makeText(this, object1.optString("message"), Toast.LENGTH_SHORT).show();
//                }
//
//                if (array == null) {
//                    dialog.dismiss();
//                    Toast.makeText(this, "" + object1.getString("message"), Toast.LENGTH_SHORT).show();
//                } else {
//                    dialog.dismiss();
//                    JSONObject jsonObject;
//                    for (int i = 0; i < array.length(); i++) {
//                        jsonObject = array.getJSONObject(i);
//                        String tagId = jsonObject.optString("tagId");
//                        assetUID = jsonObject.optString("assetUID");
//                        String plant = jsonObject.optString("plant");
//                        costCenter = jsonObject.optString("costCenter");
//                        location = jsonObject.optString("location");
//                        String last_inv_on = jsonObject.optString("last_inv_on");
//                        String assetGuid = jsonObject.optString("assetGuid");
//                        String assetDesc = jsonObject.optString("assetDesc");
//                        String status = jsonObject.optString("status");
//                        String lastSeenLoc = jsonObject.optString("lastSeenLoc");
//                        String performedBy = jsonObject.optString("performedBy");
//                        String performedOn = jsonObject.optString("performedOn");
//                        String serialNo = jsonObject.optString("serialNo");
//                        String qty = jsonObject.optString("qty");
//                        String businessArea = jsonObject.optString("businessArea");
//                        String capitaliationDate = jsonObject.optString("capitaliationDate");
//                        String statusCode = jsonObject.optString("statusCode");
//                        String message = jsonObject.optString("message");
//                        stringList.add(new LocalDataBase(tagId, assetUID, plant, costCenter, location, last_inv_on, assetGuid, assetDesc, "M", lastSeenLoc
//                                , performedBy, performedOn, serialNo, qty, businessArea, capitaliationDate));
//                        templist.add(tagId);
//
//
//                    }
//                }
//                System.out.println("response" + response);
//
//                if (stringList.size() > 0) {
//                    adapter_audit = new Adapter_Audit(this, stringList, templist);
//                    listViewAudit.setAdapter(adapter_audit);
//                    notFound.setText(String.valueOf(stringList.size()));
//                    Total.setText(String.valueOf(stringList.size()));
////                    total = stringList.size();
//                } else {
//                    Toast.makeText(this, "No data Available", Toast.LENGTH_SHORT).show();
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                dialog.dismiss();
////                Toast.makeText(this, "Technical Error...", Toast.LENGTH_SHORT).show();
//            }
//
//
//            Log.i("VOLLEY Submit", response);
////
//        }, error -> {
//            BtnStatus = false;
//            dialog.dismiss();
//            try {
//                Log.e("VOLLEY Negative", String.valueOf(error.networkResponse.statusCode));
//                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));
//                if (error.networkResponse.statusCode == 404) {
//                    Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show();
//                } else if (error.networkResponse.statusCode == 400) {
//                    Toast.makeText(this, "Bad Request", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Unable to process the request", Toast.LENGTH_SHORT).show();
//
//                }
//            } catch (Exception e) {
//                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));
//
//
//            }
////            dialog.dismiss();
//        }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                    return null;
//                }
//            }
//
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                System.out.println("Response Code " + response.statusCode);
//                return super.parseNetworkResponse(response);
//            }
//        };
//
//        queue.add(stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
//    }


    private void BatchLst() {
        int batchSize = 100;
        int batchNO = 0;
        int totalItems = stringList.size();
        int numBatches = (int) Math.ceil((double) totalItems / batchSize);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < numBatches; i++) {
                    int startIndex = i * batchSize;
                    int endIndex = Math.min(startIndex + batchSize, totalItems);
                    List<ModelClass> batch = stringList.subList(startIndex, endIndex);
//                    try {
////                        submit_Report(batch);
////                Toast.makeText(this, "BATCH NO : "+batchNO, Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }).start();

    }


    private void Clear() {
        Found.setText("0");
        total = 0;
        found = 0;
        notfound = 0;
        MisLo = 0;
        Total.setText("0");
        Miscellaneous.setText("0");
        notFound.setText("0");
        stringList.clear();
        templist.clear();
        Adapter_Audit.founditem = 0;
        Adapter_Audit.bookFooundCount = 0;
        adapter_audit = new Adapter_Audit(this, stringList, templist);
        listViewAudit.setAdapter(adapter_audit);
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            UHFTAGInfo info = (UHFTAGInfo) msg.obj;
            String epcv = info.getEPC();
            if (!rfidlist.contains(epcv)) {
                adapter_audit.getFilter(epcv);
                rfidlist.add(epcv);
            }
            total = stringList.size();
            found = adapter_audit.bookFooundCount;
            MisLo = adapter_audit.founditem;
            notfound = total - found - MisLo;
            Found.setText(String.valueOf(found));
            Total.setText(String.valueOf(total));
            Miscellaneous.setText(String.valueOf(MisLo));
            notFound.setText(String.valueOf(notfound));
            System.out.println("EPC" + info.getEPC());
        }
    };

}