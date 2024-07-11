package com.example.LeaveApplicationPortal.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "holidays")
public class Holidays {

    private String occassion;
    private String date;
    private String day;

    public Holidays(String occassion, String date, String day) {
        this.occassion = occassion;
        this.date = date;
        this.day = day;
    }

    public Holidays() {
    }

    public String getOccassion() {
        return occassion;
    }

    public void setOccassion(String occassion) {
        this.occassion = occassion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    @Override
    public String toString() {
        return "Holidays{" +
                "occassion='" + occassion + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}
