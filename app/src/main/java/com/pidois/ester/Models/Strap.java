package com.pidois.ester.Models;

public class Strap {

    int tremorPos1;
    int tremorPos2;
    int tremorPos3;

    public Strap (int tremorPos1, int tremorPos2, int tremorPos3){
        this.tremorPos1 = tremorPos1;
        this.tremorPos2 = tremorPos2;
        this.tremorPos3 = tremorPos3;
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

    public void setTremorPos1(int tremorPos1) {
        this.tremorPos1 = tremorPos1;
    }

    public void setTremorPos2(int tremorPos2) {
        this.tremorPos2 = tremorPos2;
    }

    public void setTremorPos3(int tremorPos3) {
        this.tremorPos3 = tremorPos3;
    }
}
