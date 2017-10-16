package com.example.nadia.complain;

public class ComplainBox {
    private String idC, nameC, phoneC, nid, postcodeC, address, cmpln;

    public ComplainBox() {

    }

    public ComplainBox(String idC, String nameC, String phoneC, String nid, String postcodeC, String address, String cmpln) {
        this.idC = idC;
        this.nameC = nameC;
        this.phoneC = phoneC;
        this.nid = nid;
        this.postcodeC = postcodeC;
        this.address = address;
        this.cmpln = cmpln;
    }

    public String getIdC() {
        return idC;
    }

    public void setIdC(String idC) {
        this.idC = idC;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getPhoneC() {
        return phoneC;
    }

    public void setPhoneC(String phoneC) {
        this.phoneC = phoneC;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPostcodeC() {
        return postcodeC;
    }

    public void setPostcodeC(String postcodeC) {
        this.postcodeC = postcodeC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCmpln() {
        return cmpln;
    }

    public void setCmpln(String cmpln) {
        this.cmpln = cmpln;
    }
}
