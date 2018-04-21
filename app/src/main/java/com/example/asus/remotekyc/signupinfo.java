package com.example.asus.remotekyc;

public class signupinfo {
    private String name,nric,dob,email,password, phone;

    public signupinfo(){
    }

    public signupinfo(String name, String email, String password, String nric, String dob, String phone){
        this.name = name;
        this.email = email;
        this.password = password;
        this.nric = nric;
        this.dob = dob;
        this.phone = phone;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setNric(String nric){
        this.nric = nric;
    }
    public void setDob(String dob){
        this.dob = dob;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getNric(){
        return nric;
    }
    public String getDob(){
        return dob;
    }
    public String getPhone() {
        return phone;
    }
}
