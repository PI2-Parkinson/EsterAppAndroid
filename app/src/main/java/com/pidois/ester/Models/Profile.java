package com.pidois.ester.Models;

public class Profile {

    //Tipo {1 - Info}, {2 - Cognitive}, {3 - Strap}
    private int type;

    //Person info
    private String name, email, birthday;

    //Strap info
    private String tremorPos1, tremorPos2, tremorPos3, date;

    //Cognitive ex info
    private String totalAnswers, rightAnswers, wrongAnswers, cognitiveDate;

    //Sound ex info
    private String soundLevel, soundRightAnswers, soundDate;

    //Color ex info
    private String colorLevel, colorRightanswers, colorDate;

    /*public Profile (String name, String email, String birthday, String tremorPos1, String tremorPos2, String tremorPos3, String date) {
        //Person constructor
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        //Strap constructor
        this.tremorPos1 = tremorPos1;
        this.tremorPos2 = tremorPos2;
        this.tremorPos3 = tremorPos3;
        this.date = date;
    }*/

    public String getColorLevel() {
        return colorLevel;
    }

    public void setColorLevel(String colorLevel) {
        this.colorLevel = colorLevel;
    }

    public String getColorRightanswers() {
        return colorRightanswers;
    }

    public void setColorRightanswers(String colorRightanswers) {
        this.colorRightanswers = colorRightanswers;
    }

    public String getColorDate() {
        return colorDate;
    }

    public void setColorDate(String colorDate) {
        this.colorDate = colorDate;
    }

    public String getSoundLevel() {
        return soundLevel;
    }

    public void setSoundLevel(String soundLevel) {
        this.soundLevel = soundLevel;
    }

    public String getSoundRightAnswers() {
        return soundRightAnswers;
    }

    public void setSoundRightAnswers(String soundRightAnswers) {
        this.soundRightAnswers = soundRightAnswers;
    }

    public String getSoundDate() {
        return soundDate;
    }

    public void setSoundDate(String soundDate) {
        this.soundDate = soundDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(String totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public String getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(String rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public String getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(String wrongAsnwers) {
        this.wrongAnswers = wrongAsnwers;
    }

    public String getCognitiveDate() {
        return cognitiveDate;
    }

    public void setCognitiveDate(String cognitiveDate) {
        this.cognitiveDate = cognitiveDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTremorPos1() {
        return tremorPos1;
    }

    public void setTremorPos1(String tremorPos1) {
        this.tremorPos1 = tremorPos1;
    }

    public String getTremorPos2() {
        return tremorPos2;
    }

    public void setTremorPos2(String tremorPos2) {
        this.tremorPos2 = tremorPos2;
    }

    public String getTremorPos3() {
        return tremorPos3;
    }

    public void setTremorPos3(String tremorPos3) {
        this.tremorPos3 = tremorPos3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
