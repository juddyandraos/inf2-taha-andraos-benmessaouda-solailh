package com.inf2.dto.user;


public class UserUpdateRequest {
    private String email;
    private String password;

    public UserUpdateRequest() {}

    public UserUpdateRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
