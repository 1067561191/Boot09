package edu.cming.bean;

import javax.validation.constraints.NotNull;

public class Student {


    @NotNull
    private String account;

    @NotNull
    private String password;


    @NotNull
    private String name;

    private String enc;

    private String despassword;

    private String fid;

    private String uid;

    private String uf;

    private String d;

    private String vc3;

    public Student() {
    }

    public Student(String account, String password, String name, String enc, String despassword, String fid, String uid, String uf, String d, String vc3) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.enc = enc;
        this.despassword = despassword;
        this.fid = fid;
        this.uid = uid;
        this.uf = uf;
        this.d = d;
        this.vc3 = vc3;
    }

    public Student(String account, String password, String name, String enc, String despassword) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.enc = enc;
        this.despassword = despassword;
    }

    public String getDespassword() {
        return despassword;
    }

    public void setDespassword(String despassword) {
        this.despassword = despassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getVc3() {
        return vc3;
    }

    public void setVc3(String vc3) {
        this.vc3 = vc3;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnc() {
        return enc;
    }

    public void setEnc(String enc) {
        this.enc = enc;
    }

    @Override
    public String toString() {
        return "Student{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", despassword='" + despassword + '\'' +
                '}';
    }
}
