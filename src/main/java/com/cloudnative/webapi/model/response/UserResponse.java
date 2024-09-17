package com.cloudnative.webapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {
    @JsonProperty("id")
    private UUID id;
    @Email
    @JsonProperty("username")
    private String username;
    @JsonProperty("first_name")
    private String firstname;
    @JsonProperty("last_name")
    private String lastname;
    @JsonProperty("account_created")
    private LocalDateTime accountCreated;
    @JsonProperty("account_updated")
    private LocalDateTime accountUpdated;
    @JsonProperty("account_verified")
    private Boolean accountVerified;

    public UserResponse() {
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public LocalDateTime getAccountCreated() {
        return this.accountCreated;
    }

    public LocalDateTime getAccountUpdated() {
        return this.accountUpdated;
    }

    public Boolean getAccountVerified() {
        return this.accountVerified;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAccountCreated(LocalDateTime accountCreated) {
        this.accountCreated = accountCreated;
    }

    public void setAccountUpdated(LocalDateTime accountUpdated) {
        this.accountUpdated = accountUpdated;
    }

    public void setAccountVerified(Boolean accountVerified) {
        this.accountVerified = accountVerified;
    }
}
