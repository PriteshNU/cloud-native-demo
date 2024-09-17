package com.cloudnative.webapi.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {
    @NotBlank(message = "Username must not be null or empty")
    @Email(message = "Invalid username")
    @JsonProperty("username")
    private String username;
    @NotBlank(message = "Password must not be null or empty")
    @JsonProperty("password")
    private String password;
    @NotBlank(message = "Firstname must not be null or empty")
    @JsonProperty("first_name")
    private String firstname;
    @NotBlank(message = "Lastname must not be null or empty")
    @JsonProperty("last_name")
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
}
