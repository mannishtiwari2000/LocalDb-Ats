package com.example.seeksoftats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LocalDataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SeekSoftATSDB";
    private static final String App_ConfigTable = "App_ConfigTable";
    private static final String AssetDetailsTable = "AssetDetailsTable";
    private static final String ID = "ID";
    private static final String ClientID = "ClientID";
    private static final String CompanyCode = "CompanyCode";
    private static final String PlantCode = "PlantCode";
    private static final String tagId = "tagId";
    private static final String assetUID = "assetUID";
    private static final String plant = "plant";
    private static final String costCenter = "costCenter";
    private static final String location = "location";
    private static final String last_inv_on = "last_inv_on";
    private static final String assetGuid = "assetGuid";
    private static final String assetDesc = "assetDesc";
    private static final String status = "status";
    private static final String lastSeenLoc = "lastSeenLoc";
    private static final String performedBy = "performedBy";
    private static final String performedOn = "performedOn";
    private static final String serialNo = "serialNo";
    private static final String qty = "qty";
    private static final String businessArea = "businessArea";
    private static final String capitaliationDate = "capitaliationDate";
    private static final String Color = "Color";

    public LocalDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String AppConfigQuery = "CREATE TABLE " + App_ConfigTable + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ClientID + " TEXT,"
                + CompanyCode + " TEXT,"
                + PlantCode + " TEXT)";


        String AssetDetailsTable = "CREATE TABLE " + App_ConfigTable + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tagId + " TEXT,"
                + assetUID + " TEXT,"
                + plant + " TEXT,"
                + costCenter + " TEXT,"
                + location + " TEXT,"
                + last_inv_on + " TEXT,"
                + assetGuid + " TEXT,"
                + assetDesc + " TEXT,"
                + status + " TEXT,"
                + lastSeenLoc + " TEXT,"
                + performedBy + " TEXT,"
                + performedOn + " TEXT,"
                + serialNo + " TEXT,"
                + qty + " TEXT,"
                + businessArea + " TEXT,"
                + capitaliationDate + " TEXT,"
                + Color + " TEXT)";
        sqLiteDatabase.execSQL(AppConfigQuery);
        sqLiteDatabase.execSQL(AssetDetailsTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + App_ConfigTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AssetDetailsTable);

        onCreate(sqLiteDatabase);
    }


    public boolean AddTagging(String RfidNo, String AssetUID) {
        boolean myState = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tagId, RfidNo);
        values.put(assetUID, AssetUID);
//        values.put(performedOn, DateofTagged);


        // Inserting Row
        long result = db.insert(AssetDetailsTable, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        System.out.print("Value insert" + result);
        if (result == 1) {
            myState = true;
        } else {
            myState = false;
        }

        return myState;
    }


    public void DataConFigDetails(AppConFigModel appConFigModel) {
        boolean myState = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClientID, appConFigModel.getClientCode());
        values.put(PlantCode, appConFigModel.getPlantCode());
        values.put(CompanyCode, appConFigModel.getCompanyCode());

        // Inserting Row
        long result = db.insert(App_ConfigTable, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        System.out.print("Value insert" + result);

    }

    public List<AppConFigModel> GetStatusInovice() {
        List<AppConFigModel> contactList = new ArrayList<AppConFigModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + App_ConfigTable;
//        String selectQuery = "SELECT  * FROM " + Invoice_DetailsTable + " WHERE " + InvoiceNo + " = '" + Inovice + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and/ adding to list
        if (cursor.moveToFirst()) {
            do {
                AppConFigModel dataModelClass = new AppConFigModel();
                dataModelClass.setClientCode(cursor.getString(0));
                dataModelClass.setPlantCode(cursor.getString(1));
                dataModelClass.setCompanyCode(cursor.getString(2));
                dataModelClass.setId(cursor.getString(3));
                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }


    public List<ModelClass> getAllDetailsSearch(String SearchID) {
        List<ModelClass> contactList = new ArrayList<ModelClass>();
        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_Report + "WHERE" + PVmodleName + "=" + Modelname;
        String selectQuery = "SELECT * FROM " + AssetDetailsTable + " WHERE " + serialNo + " = '" + SearchID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelClass dataModelClass = new ModelClass();
                dataModelClass.setTagId(cursor.getString(0));
                dataModelClass.setLocation(cursor.getString(1));
                dataModelClass.setLastSeenLoc(cursor.getString(2));

                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    public List<ModelClass> getAllAsseTDetails(String Tagid) {
        List<ModelClass> contactList = new ArrayList<ModelClass>();
        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_Report + "WHERE" + PVmodleName + "=" + Modelname;
        String selectQuery = "SELECT * FROM " + AssetDetailsTable + " WHERE " + tagId + " = '" + Tagid + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelClass dataModelClass = new ModelClass();
                dataModelClass.setTagId(cursor.getString(0));
                dataModelClass.setAssetUID(cursor.getString(1));
                dataModelClass.setPlant(cursor.getString(2));
                dataModelClass.setCostCenter(cursor.getString(3));
                dataModelClass.setLocation(cursor.getString(4));
                dataModelClass.setLast_inv_on(cursor.getString(5));
                dataModelClass.setAssetGuid(cursor.getString(6));
                dataModelClass.setAssetDesc(cursor.getString(7));
                dataModelClass.setStatus(cursor.getString(8));
                dataModelClass.setLastSeenLoc(cursor.getString(9));
                dataModelClass.setPerformedBy(cursor.getString(10));
                dataModelClass.setPerformedOn(cursor.getString(11));
                dataModelClass.setSerialNo(cursor.getString(12));
                dataModelClass.setQty(cursor.getString(13));
                dataModelClass.setBusinessArea(cursor.getString(14));
                dataModelClass.setCapitaliationDate(cursor.getString(15));
                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    public List<ModelClass> GetAuditList() {
        List<ModelClass> contactList = new ArrayList<ModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + AssetDetailsTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and/ adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelClass dataModelClass = new ModelClass();
                dataModelClass.setTagId(cursor.getString(0));
                dataModelClass.setAssetUID(cursor.getString(1));
                dataModelClass.setPlant(cursor.getString(2));
                dataModelClass.setCostCenter(cursor.getString(3));
                dataModelClass.setLocation(cursor.getString(4));
                dataModelClass.setLast_inv_on(cursor.getString(5));
                dataModelClass.setAssetGuid(cursor.getString(6));
                dataModelClass.setAssetDesc(cursor.getString(7));
                dataModelClass.setStatus(cursor.getString(8));
                dataModelClass.setLastSeenLoc(cursor.getString(9));
                dataModelClass.setPerformedBy(cursor.getString(10));
                dataModelClass.setPerformedOn(cursor.getString(11));
                dataModelClass.setSerialNo(cursor.getString(12));
                dataModelClass.setQty(cursor.getString(13));
                dataModelClass.setBusinessArea(cursor.getString(14));
                dataModelClass.setCapitaliationDate(cursor.getString(15));

                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }


}
