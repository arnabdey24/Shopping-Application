package com.xyz;

import java.sql.Connection;

public class Session {
    public String uid;
    public int isImp;
    public Connection con;
    public double height;
    public double weight;

    public Session(String uid, int isImp ,Connection con,double height,double weight) {
        this.uid = uid;
        this.isImp = isImp;
        this.con=con;
        this.height=height;
        this.weight=weight;
    }
}
