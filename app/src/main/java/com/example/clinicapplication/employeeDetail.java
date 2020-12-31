package com.example.clinicapplication;

public class employeeDetail {
    String employee_name, employee_age, employee_phone, employee_role;

    public employeeDetail(String employee_name, String employee_age, String employee_phone, String employee_role) {
        this.employee_name = employee_name;
        this.employee_age = employee_age;
        this.employee_phone = employee_phone;
        this.employee_role = employee_role;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_age() {
        return employee_age;
    }

    public void setEmployee_age(String employee_age) {
        this.employee_age = employee_age;
    }

    public String getEmployee_phone() {
        return employee_phone;
    }

    public void setEmployee_phone(String employee_phone) {
        this.employee_phone = employee_phone;
    }

    public String getEmployee_role() {
        return employee_role;
    }

    public void setEmployee_role(String employee_role) {
        this.employee_role = employee_role;
    }
}
