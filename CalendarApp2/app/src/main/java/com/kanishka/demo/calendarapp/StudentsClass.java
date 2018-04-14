package com.kanishka.demo.calendarapp;

public class StudentsClass {

    private String s_name;
    private String s_attendance;

    public StudentsClass(String name, String attendance) {
        s_name = name;
        s_attendance = attendance;
    }

    public String getName() {
        return s_name;
    }
    public String getAttendance() {
        return s_attendance;
    }

}