package com.example.notificationhelper.Model;

public class MyContacts {
    String name, number;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    boolean select;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public MyContacts(String name, String number, boolean select){
        this.name = name;
        this.number = number;
        this.select = select;
    }
}
