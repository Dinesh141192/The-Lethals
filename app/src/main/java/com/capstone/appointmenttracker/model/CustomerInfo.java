package com.capstone.appointmenttracker.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class CustomerInfo implements Serializable {
    private long id, createdTime, appointmentDay, appointmentTime;
    private String fname, lname, phoneNo, emailID, comments, assignedTo;
    private int age, attended, cancelled;

    public CustomerInfo(long id, String fname, String lname, String emailID, String comments, String assignedTo, String phoneNo,int age, long createdTime, long appointmentDay, long appointmentTime, int attended, int cancelled){
    this.id = id;
    this.fname = fname;
    this.lname = lname;
    this.emailID = emailID;
    this.comments = comments;
    this.assignedTo = assignedTo;
    this.phoneNo = phoneNo;
    this.age = age;
    this.createdTime = createdTime;
    this.appointmentDay = appointmentDay;
    this.appointmentTime = appointmentTime;
    this.attended = attended;
    this.cancelled = cancelled;
    }
    public  CustomerInfo(String fname, String lname, String emailID, String comments, String assignedTo, String phoneNo,int age, long createdTime, long appointmentDay,long appointmentTime, int attended, int cancelled){
      this(new Random().nextLong(),fname, lname, emailID, comments, assignedTo, phoneNo,age,createdTime,appointmentDay,appointmentTime,attended,cancelled);
    }

    public long getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(long appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    public int getAttended() {
        return attended;
    }

    public int getCancelled() {
        return cancelled;
    }

    public long getId() {
        return id;
    }

    public long getCreatedTime() {
        return createdTime;
    }



    public String getPhoneNo() {
        return phoneNo;
    }

    public int getAge() {
        return age;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(long appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getComments() {
        return comments;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerInfo that = (CustomerInfo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
