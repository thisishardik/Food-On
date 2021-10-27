package com.example.foodon;

public class Chef {
    private String area, city, confirmPassword, emailId, fName, house, lName, mobile, password, postCode, state;

    public Chef(String area, String city, String confirmPassword, String emailId, String fName, String house, String lName, String mobile, String password, String postCode, String state) {
        this.area = area;
        this.city = city;
        this.confirmPassword = confirmPassword;
        this.emailId = emailId;
        this.fName = fName;
        this.house = house;
        this.lName = lName;
        this.mobile = mobile;
        this.password = password;
        this.postCode = postCode;
        this.state = state;
    }

    public Chef() {
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getfName() {
        return fName;
    }

    public String getHouse() {
        return house;
    }

    public String getlName() {
        return lName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getState() {
        return state;
    }
}
