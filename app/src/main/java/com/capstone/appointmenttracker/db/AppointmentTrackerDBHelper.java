package com.capstone.appointmenttracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.capstone.appointmenttracker.model.CustomerInfo;
import com.capstone.appointmenttracker.model.CustomerInfoList;
import com.capstone.appointmenttracker.utils.AppointmentUtils;

import java.util.ArrayList;
import java.util.Date;

public class AppointmentTrackerDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static AppointmentTrackerDBHelper INSTANCE;

    private static final String SQL_CREATE = "CREATE TABLE " + AppointmentTrackerContract.AppointmentTrackerEntry.TABLE_NAME+" ("+
            AppointmentTrackerContract.AppointmentTrackerEntry._ID+ " INTEGER PRIMARY KEY,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_FNAME+ " TEXT,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_LNAME+ " TEXT,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_EMAILID+ " TEXT,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_COMMENTS+ " TEXT,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ASSIGNEDTO+ " TEXT,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_AGE+ " INTEGER,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_PHONENO+ " TEXT,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTDAY+ " INTEGER,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTTIME+ " INTEGER,"+
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_CREATEDTIME+ " INTEGER," +
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ISATTENDED+ " INTEGER DEFAULT 0," +
            AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ISCANCELLED+ " INTEGER DEFAULT 0" + ")";

    public static AppointmentTrackerDBHelper getInstance(){
        return INSTANCE;
    }

    public static void initialize(Context context){
        INSTANCE = new AppointmentTrackerDBHelper(context);
        CustomerInfoList.initialize();
    }


    private AppointmentTrackerDBHelper(@Nullable Context context) {
        super(context, AppointmentTrackerContract.DATABASE_NAME, null, AppointmentTrackerContract.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
        //insert sample data
        //insert(db,new CustomerInfo("Dinesh","Reddy","c.dineshreddy@gmail.com","nothing","Ramu","9999999999",27, new Date().getTime(),new Date().getTime(), 0,0));
        //insert(db,new CustomerInfo("Prasanna","Mamidi","prasanna@gmail.com","nothing","Ramu","8888888888",22, new Date().getTime(),new Date().getTime(),0,0));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insert(SQLiteDatabase database, CustomerInfo customerInfo){
        ContentValues values = new ContentValues();
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry._ID,customerInfo.getId());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_FNAME,customerInfo.getFname());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_LNAME,customerInfo.getLname());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_EMAILID,customerInfo.getEmailID());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_COMMENTS,customerInfo.getComments());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ASSIGNEDTO,customerInfo.getAssignedTo());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_AGE,customerInfo.getAge());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_PHONENO,customerInfo.getPhoneNo());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTDAY,customerInfo.getAppointmentDay());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTTIME,customerInfo.getAppointmentTime());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_CREATEDTIME,customerInfo.getCreatedTime());
        database.insert(AppointmentTrackerContract.AppointmentTrackerEntry.TABLE_NAME,null,values);
        //update App widget

    }

    public void insert(CustomerInfo customerInfo){
        SQLiteDatabase database = getWritableDatabase();
        insert(database,customerInfo);
    }
    public void update(CustomerInfo customerInfo) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_FNAME,customerInfo.getFname());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_LNAME,customerInfo.getLname());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_EMAILID,customerInfo.getEmailID());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_COMMENTS,customerInfo.getComments());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ASSIGNEDTO,customerInfo.getAssignedTo());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_AGE,customerInfo.getAge());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_PHONENO,customerInfo.getPhoneNo());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTDAY,customerInfo.getAppointmentDay());
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTTIME,customerInfo.getAppointmentTime());


        database.update(AppointmentTrackerContract.AppointmentTrackerEntry.TABLE_NAME, values, AppointmentTrackerContract.AppointmentTrackerEntry._ID +" = ?", new String[]{String.valueOf(customerInfo.getId())});
    }

    public void delete(CustomerInfo customerInfo){
        SQLiteDatabase database = getWritableDatabase();
        String where = AppointmentTrackerContract.AppointmentTrackerEntry._ID + "= ?";
        String[] whereArgs = {String.valueOf(customerInfo.getId())};
        database.delete(AppointmentTrackerContract.AppointmentTrackerEntry.TABLE_NAME,where,whereArgs);
        //update app widget
    }

    public ArrayList<CustomerInfo> getCustomerInfo(){
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<CustomerInfo> result = new ArrayList<>();
        String sortOrder = AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTTIME+ " DESC";
        Cursor cursor = database.query(
                AppointmentTrackerContract.AppointmentTrackerEntry.TABLE_NAME,
                null,AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ISATTENDED + "= ?" + " and "  +
                        AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ISCANCELLED + "= ?",
                new String[] {"0","0"},
                null,
                null,
                sortOrder

        );


        while (cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry._ID));

            String fname = cursor.getString(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_FNAME));
            String lname = cursor.getString(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_LNAME));
            String emailID = cursor.getString(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_EMAILID));
            String comments = cursor.getString(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_COMMENTS));
            String assignedTo = cursor.getString(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ASSIGNEDTO));
            String phoneNo = cursor.getString(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_PHONENO));
            int age = cursor.getInt(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_AGE));
            long createdTime = cursor.getLong(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_CREATEDTIME));
            long appointmentDay = cursor.getLong(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTDAY));
            long appointmentTime = cursor.getLong(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_APPOINTMENTTIME));
            int cancelled = cursor.getInt(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ISCANCELLED));
            int attended = cursor.getInt(cursor.getColumnIndex(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ISATTENDED));
            result.add(new CustomerInfo(id, fname, lname, emailID, comments,assignedTo, phoneNo, age, createdTime, appointmentDay,appointmentTime,attended,cancelled));
        }
        cursor.close();
        return result;
    }


    public void addCancelled(CustomerInfo customerInfo) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ISCANCELLED,"1");
        database.update(AppointmentTrackerContract.AppointmentTrackerEntry.TABLE_NAME, values, AppointmentTrackerContract.AppointmentTrackerEntry._ID +" = ?", new String[]{String.valueOf(customerInfo.getId())});

    }

    public void addUpdated(CustomerInfo customerInfo) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppointmentTrackerContract.AppointmentTrackerEntry.COLUMN_NAME_ISATTENDED,"1");
        database.update(AppointmentTrackerContract.AppointmentTrackerEntry.TABLE_NAME, values, AppointmentTrackerContract.AppointmentTrackerEntry._ID +" = ?", new String[]{String.valueOf(customerInfo.getId())});

    }
}
