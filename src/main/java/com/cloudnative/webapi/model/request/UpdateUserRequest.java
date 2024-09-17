package com.cloudnative.webapi.model.request;

import com.cloudnative.webapi.validation.AllowNullButNotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserRequest {
    @JsonIgnore
    private String username;
    @JsonProperty("password")
    @AllowNullButNotEmpty(message = "Password cannot be empty")
    private String password;
    @JsonProperty("first_name")
    @AllowNullButNotEmpty(message = "Firstname cannot be empty")
    private String firstname;
    @JsonProperty("last_name")
    @AllowNullButNotEmpty(message = "Lastname cannot be empty")
    private String lastname;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
