package com.example.clinicapplication;

public class appointmentDetail {
    String service, time, date, performed;

    public appointmentDetail(String service, String time, String date, String performed) {
        this.service = service;
        this.time = time;
        this.date = date;
        this.performed = performed;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPerformed() {
        return performed;
    }

    public void setPerformed(String performed) {
        this.performed = performed;
    }
}
