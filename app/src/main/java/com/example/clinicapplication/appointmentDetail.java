package com.example.clinicapplication;

public class appointmentDetail {
    String service, time, date, performed, client, code;

    public appointmentDetail(String service, String time, String date, String performed, String client, String code) {
        this.service = service;
        this.time = time;
        this.date = date;
        this.performed = performed;
        this.client = client;
        this.code = code;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
