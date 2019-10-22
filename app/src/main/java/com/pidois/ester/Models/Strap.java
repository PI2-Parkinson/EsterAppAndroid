package com.pidois.ester.Models;

import com.pidois.ester.ProfileItem;

import java.util.Date;

public class Strap implements ProfileItem {

    private int tremorPos1;
    private int tremorPos2;
    private int tremorPos3;
    private Date date;


    public Strap (int tremorPos1, int tremorPos2, int tremorPos3, Date date){
        this.tremorPos1 = tremorPos1;
        this.tremorPos2 = tremorPos2;
        this.tremorPos3 = tremorPos3;
        this.date = date;
    }

    public int getTremorPos1() {
        return tremorPos1;
    }

    public int getTremorPos2() {
        return tremorPos2;
    }

    public int getTremorPos3() {
        return tremorPos3;
    }

    public Date getDate() {
        return date;
    }

    public void setTremorPos1(int tremorPos1) {
        this.tremorPos1 = tremorPos1;
    }

    public void setTremorPos2(int tremorPos2) {
        this.tremorPos2 = tremorPos2;
    }

    public void setTremorPos3(int tremorPos3) {
        this.tremorPos3 = tremorPos3;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return ProfileItem.TYPE_STRAP;
    }
}
