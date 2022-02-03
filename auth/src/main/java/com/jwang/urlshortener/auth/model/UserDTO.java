package com.jwang.urlshortener.auth.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {
    @NotEmpty(message = "username must not be empty")
    @Size(min = 5, max = 15, message = "username must be between 5 and 15 chars")
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 7, max = 15,message = "password must be between 7 and 15 chars")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
