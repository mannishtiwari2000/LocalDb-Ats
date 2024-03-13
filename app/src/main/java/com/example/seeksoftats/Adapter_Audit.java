package com.example.seeksoftats;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Audit extends BaseAdapter {
    private Context context;
    List<ModelClass> list;
    List<String> templist;
    boolean b = false;
    List<String> MYTEmp = new ArrayList<>();

    public Adapter_Audit(Context context, List<ModelClass> list, List<String> templist) {
        this.context = context;
        this.list = list;
        this.templist = templist;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder holder;
        if (view == null) {
            holder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);

            holder.itemname_list = (TextView) view.findViewById(R.id.itemname_list);
            holder.itemdetails1 = (TextView) view.findViewById(R.id.itemDetails1);
            holder.itemdetails2 = (TextView) view.findViewById(R.id.itemDetails2);
            holder.heading1 = (TextView) view.findViewById(R.id.heading1);
            holder.heading2 = (TextView) view.findViewById(R.id.heading2);
            holder.heading3 = (TextView) view.findViewById(R.id.heading3);

            holder.listlayout = (ConstraintLayout) view.findViewById(R.id.listlayout);

            view.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (Viewholder) view.getTag();

        }


//
//        holder.itemname_list.setText(list.get(i).getAssetUID());
//        holder.itemdetails1.setText(list.get(i).getAssetDesc());
//        holder.itemdetails2.setText(list.get(i).getAssetGuid());
        holder.heading1.setText("Asset UID :");
        holder.heading2.setText("Asset Desc :");
//        holder.heading3.setText("Asset GUID :");

//        if (list.get(i).getStatus() == "V") {
//            holder.listlayout.setBackgroundColor(Color.rgb(46, 139, 87));
//            holder.itemname_list.setTextColor(Color.WHITE);
//            holder.itemdetails1.setTextColor(Color.WHITE);
//            holder.itemdetails2.setTextColor(Color.WHITE);
//            holder.heading3.setTextColor(Color.WHITE);
//            holder.heading1.setTextColor(Color.WHITE);
//            holder.heading2.setTextColor(Color.WHITE);
//        } else if (list.get(i).getStatus() == "L") {
//            holder.listlayout.setBackgroundColor(Color.rgb(255, 165, 0));
//            holder.itemname_list.setTextColor(Color.WHITE);
//            holder.itemdetails1.setTextColor(Color.WHITE);
//            holder.itemdetails2.setTextColor(Color.WHITE);
//            holder.heading3.setTextColor(Color.WHITE);
//            holder.heading1.setTextColor(Color.WHITE);
//            holder.heading2.setTextColor(Color.WHITE);
//        } else {
//            holder.listlayout.setBackgroundColor(Color.WHITE);
//            holder.itemname_list.setTextColor(Color.RED);
//            holder.itemdetails1.setTextColor(Color.RED);
//            holder.itemdetails2.setTextColor(Color.RED);
//            holder.heading3.setTextColor(Color.RED);
//            holder.heading1.setTextColor(Color.RED);
//            holder.heading2.setTextColor(Color.RED);
//        }
//        holder.itemdetails1.setText(String.valueOf(assetList.asset1List.get(position).getAssetDesc()));
//        holder.itemdetails2.setText(String.valueOf(assetList.asset1List.get(position).getTagId()));

        return view;
    }

    public static int bookFooundCount = 0;
    public static int founditem = 0;


    public void getFilter(String search_value) {

        if (!MYTEmp.contains(search_value)){
            if (templist.size() > 0) {
                if (templist.contains(search_value)) {
                    for (ModelClass row : list) {

                        if (row.getTagId().equals(search_value)) {
                            templist.remove(search_value);
                            row.setColor("Green");
                            row.setStatus("V");
                            bookFooundCount = 1 + bookFooundCount;
                            notifyDataSetChanged();
                            break;
                        }
                    }
                } else {

                }
            }
            MYTEmp.add(search_value);
        }

    }

    private class Viewholder {
        TextView itemname_list, itemdetails1, itemdetails2, heading1, heading2, heading3;
        ConstraintLayout listlayout;
    }


//    private void SubmitApi(String Rfidnumber) throws JSONException {
//
////        String url = "http://belapi.netpaze.com/api/assetdetail/".concat(Rfidnumber);
//        String url = WelcomeScreenActivity.baseURLStr.concat("assetdetail/").concat(Rfidnumber);
//        RequestQueue queue = Volley.newRequestQueue(context);
//        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//
//                    String tagId = jsonObject.optString("tagId");
//                    String assetUID = jsonObject.optString("assetUID");
//                    String plant = jsonObject.optString("plant");
//                    String costCenter = jsonObject.optString("costCenter");
//                    String location = jsonObject.optString("location");
//                    String last_inv_on = jsonObject.optString("last_inv_on");
//                    String assetGuid = jsonObject.optString("assetGuid");
//                    String assetDesc = jsonObject.optString("assetDesc");
//                    String status = jsonObject.optString("status");
//                    String lastSeenLoc = jsonObject.optString("lastSeenLoc");
//                    String performedBy = jsonObject.optString("performedBy");
//                    String performedOn = jsonObject.optString("tagId");
//                    String serialNo = jsonObject.optString("performedOn");
//                    String qty = jsonObject.optString("qty");
//                    String businessArea = jsonObject.optString("businessArea");
//                    String capitaliationDate = jsonObject.optString("capitaliationDate");
//                    String statusCode = jsonObject.optString("statusCode");
//                    String message = jsonObject.optString("message");
//                    if (message.matches("success")) {
//                        list.add(new LocalDataBase(tagId, assetUID, plant, costCenter, location, last_inv_on, assetGuid, assetDesc, "L", lastSeenLoc
//                                , performedBy, performedOn, serialNo, qty, businessArea, capitaliationDate));
//                        founditem = 1 + founditem;
//                        notifyDataSetChanged();
//
//                    }
//                    b=false;
//                    Log.e("response", response.toString());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, error -> {
//
//            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
//        });
//
//
//        queue.add(jsObjRequest);
//
//    }




}
