package com.example.seeksoftats;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginForm extends AppCompatActivity {
    private Button btn_LA_Login;
    EditText edt_LA_pwd, edt_LA_userId;
    TextView txt_LA_forgetPWd;
    String Username, pasword;
    List<String> modulelist = new ArrayList<>();
    SharedPreferences prefs;
    ProgressDialog dialog;
    boolean Logstatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        btn_LA_Login = findViewById(R.id.btnLogin);
        edt_LA_pwd = findViewById(R.id.edt_password);
        edt_LA_userId = findViewById(R.id.edt_userid);
        txt_LA_forgetPWd = findViewById(R.id.edt_Forget_pwd);
        dialog = new ProgressDialog(this);
        prefs = getSharedPreferences("Login_Details", Context.MODE_PRIVATE);

        txt_LA_forgetPWd.setOnClickListener(view -> Toast.makeText(LoginForm.this, "Forget Password...", Toast.LENGTH_SHORT).show());
        btn_LA_Login.setOnClickListener(view -> {
                    if (!Logstatus) {
                        Logstatus = true;
                        pasword = edt_LA_pwd.getText().toString();
                        Username = edt_LA_userId.getText().toString();

                        if (Username.length() == 0 || pasword.length() == 0) {
                            if (Username.length() == 0) {
                                edt_LA_userId.setError("Enter Password");
                            } else if (pasword.length() == 0) {
                                edt_LA_pwd.setError("Enter Password");
                            }
                        } else {
                            try {

                                dialog.setMessage("Logging...");
                                dialog.setCancelable(false);
                                dialog.show();
                                SubmitApi(Username, pasword);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }

        );

    }

    private void SubmitApi(String username, String pasword) throws JSONException {
//        String url = "http://belapi.netpaze.com/api/login";
        String url = MainActivity.baseURLStr.concat("login");
        JSONObject obj = new JSONObject();

        obj.put("userName", username);
        obj.put("password", pasword);
//        obj.put("clientCode", WelcomeScreenActivity.ClientCode);
//        obj.put("companyCode", WelcomeScreenActivity.ComanyCode);
//        obj.put("plantCode", WelcomeScreenActivity.PlantCode);
        RequestQueue queue = Volley.newRequestQueue(this);
        final String requestBody = obj.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
//                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            try {
                JSONObject object1 = new JSONObject(response);
                String message = object1.getString("statusMessage");
                String remark = object1.getString("remark");
                String userName = object1.getString("userName");
                String userId = object1.getString("userId");
                Logstatus = false;
                JSONArray array = object1.getJSONArray("roles");
                SharedPreferences.Editor editor = prefs.edit();

                for (int i = 0; i < array.length(); i++) {
                    modulelist.add(array.getString(i));
                }

                if (message.matches("success")) {


                    Intent intent = new Intent(this, Dashboard.class);
//                    intent.putExtra("mylist", (Serializable) modulelist);
                    String convertedData = new Gson().toJson(modulelist);
                    editor.putString("username", userName);
                    editor.putString("userId", userId);
                    editor.putString("ModuleList", convertedData);
                    editor.commit();
                    this.startActivity(intent);
                    finish();
                    Toast.makeText(this, remark, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Something Wrong... ", Toast.LENGTH_SHORT).show();
                }

                Log.d("Update Data Message", response);

                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
                Logstatus = false;
            }


//            dialog.dismiss();
        }, error -> {
            dialog.dismiss();
            try {
                Logstatus = false;
                Log.e("VOLLEY Negative", String.valueOf(error.networkResponse.statusCode));
                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(LoginForm.this, "No Result Found", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(LoginForm.this, "Bad Request", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginForm.this, "Unable to process the request", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));

//                    Toast.makeText(this, "Update...", Toast.LENGTH_SHORT).show();

            }
//            dialog.dismiss();
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                System.out.println("Response Code " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "Can't Back...", Toast.LENGTH_SHORT).show();
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}





