package com.example.clinicapplication;

public class clinicDetail {
    String address, phone, working_hours, working_days, payment;

    public clinicDetail(String address, String phone, String working_hours, String working_days, String payment) {
        this.address = address;
        this.phone = phone;
        this.working_hours = working_hours;
        this.working_days = working_days;
        this.payment = payment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    public String getWorking_days() {
        return working_days;
    }

    public void setWorking_days(String working_days) {
        this.working_days = working_days;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
