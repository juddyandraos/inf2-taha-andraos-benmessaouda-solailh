package com.inf2.dto.user;


public class UserUpdateRequest {
    private String email;

    public UserUpdateRequest() {}

    public UserUpdateRequest(String email) {
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

}
