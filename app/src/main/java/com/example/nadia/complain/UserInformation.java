package com.example.nadia.complain;

/**
 * Created by User on 14-Oct-17.
 */

public class UserInformation {
    private String name, email, phone, policeId, rank, postcode, password;

    public UserInformation() {

    }

    public UserInformation(String name, String email, String phone, String policeId, String rank, String postcode, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.policeId = policeId;
        this.rank = rank;
        this.postcode = postcode;
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
