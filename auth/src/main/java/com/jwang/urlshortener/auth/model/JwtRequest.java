package com.jwang.urlshortener.auth.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotEmpty(message = "username must not be empty")
    @Size(min = 5, max = 15, message = "username must be between 5 and 15 chars")
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 7, max = 15,message = "password must be between 7 and 15 chars")
    private String password;


    //need default constructor for JSON Parsing
    public JwtRequest()
    {

    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
