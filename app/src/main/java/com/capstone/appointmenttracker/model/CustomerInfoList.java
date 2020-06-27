package com.capstone.appointmenttracker.model;

import android.util.Log;

import com.capstone.appointmenttracker.db.AppointmentTrackerDBHelper;

import java.util.ArrayList;

public class CustomerInfoList {
    private static CustomerInfoList OBJECT;
    private ArrayList<CustomerInfo> customerInfoList;

    public static void initialize(){
        OBJECT = new CustomerInfoList();
    }

    private CustomerInfoList(){
        //get customer list from database
        customerInfoList = AppointmentTrackerDBHelper.getInstance().getCustomerInfo();
    }
    public static CustomerInfoList getOBJECT(){
        return OBJECT;
    }

    public int getCount(){
        Log.e( "getCount: ", ""+customerInfoList.size());
        customerInfoList = AppointmentTrackerDBHelper.getInstance().getCustomerInfo();
        return customerInfoList.size();
    }
    public CustomerInfo get(int position){
        return customerInfoList.get(position);

    }

    public void add(String fname, String lname,String emailID, String comments, String assignedTo, String phoneNo,int age, long createdTime, long appointmentDay, long appointmentTime){
        CustomerInfo customerInfo = new CustomerInfo(fname, lname, emailID, comments, assignedTo, phoneNo,age,createdTime, appointmentDay,appointmentTime,0,0);
        customerInfoList.add(0,customerInfo);
        //insert to database
        AppointmentTrackerDBHelper.getInstance().insert(customerInfo);
    }

    public void update(long id,CustomerInfo customerInfo){
        //CustomerInfo customerInfo = new CustomerInfo(name, phoneNo,age,createdTime, appointmentTime);
        Log.e("update: ", ""+id);
        for(CustomerInfo info: customerInfoList ){
            Log.e("update: ", ""+(info.getId() == id));
            if(info.getId() == id){
                int index = customerInfoList.indexOf(info);
                customerInfoList.set(index,customerInfo);
                //update to database
                Log.e( "phone num: ", ""+customerInfo.getPhoneNo());
                AppointmentTrackerDBHelper.getInstance().update(customerInfo);
            }
        }
        //customerInfoList.add(0,customerInfo);

    }

    public void remove(CustomerInfo customerInfo){
        customerInfoList.remove(customerInfo);
        //remove from database
        AppointmentTrackerDBHelper.getInstance().delete(customerInfo);
    }

    public void addCancelledStatus(CustomerInfo customerInfo){
        AppointmentTrackerDBHelper.getInstance().addCancelled(customerInfo);

    }
    public void addAttendedStatus(CustomerInfo customerInfo){
        AppointmentTrackerDBHelper.getInstance().addUpdated(customerInfo);
    }
}
