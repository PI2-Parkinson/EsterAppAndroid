package com.pidois.ester.Models;

public class Strap {

    private String tremorPos1;
    private String tremorPos2;
    private String tremorPos3;
    private String date;


    public Strap(String tremorPos1, String tremorPos2, String tremorPos3, String date) {
        this.tremorPos1 = tremorPos1;
        this.tremorPos2 = tremorPos2;
        this.tremorPos3 = tremorPos3;
        this.date = date;
    }

    public String getTremorPos1() {
        return tremorPos1;
    }

    public String getTremorPos2() {
        return tremorPos2;
    }

    public String getTremorPos3() {
        return tremorPos3;
    }

    public String getDate() {
        return date;
    }

    public void setTremorPos1(String tremorPos1) {
        this.tremorPos1 = tremorPos1;
    }

    public void setTremorPos2(String tremorPos2) {
        this.tremorPos2 = tremorPos2;
    }

    public void setTremorPos3(String tremorPos3) {
        this.tremorPos3 = tremorPos3;
    }

    public void setDate(String date) {
        this.date = date;
    }

}