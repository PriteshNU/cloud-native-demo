package com.cloudnative.webapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, name = "username", unique = true)
    private String username;
    @Column(nullable = false, name = "password")
    private String password;
    @Column(nullable = false, name = "first_name")
    private String firstName;
    @Column(nullable = false, name = "last_name")
    private String lastName;
    @Column(nullable = false, name = "account_created")
    private LocalDateTime accountCreated;
    @Column(nullable = false, name = "account_updated")
    private LocalDateTime accountUpdated;
    @Column(nullable = false, name = "account_verified")
    private Boolean accountVerified = false;
    @Column(name = "verification_token", unique = true)
    private String verificationToken;
    @Column(name = "verification_token_expiry")
    private LocalDateTime verificationTokenExpiry;

    public User() {
    }

    public User(String username,
                String password,
                String firstname,
                String lastname) {
        this.username = username;
        this.password = password;
        this.firstName = firstname;
        this.lastName = lastname;
    }

    @PrePersist
    protected void onCreate() {
        this.accountCreated = LocalDateTime.now();
        this.accountUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.accountUpdated = LocalDateTime.now();
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstname() {
        return this.firstName;
    }

    public String getLastname() {
        return this.lastName;
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

    public String getVerificationToken() {
        return this.verificationToken;
    }

    public LocalDateTime getVerificationTokenExpiry() {
        return this.verificationTokenExpiry;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public void setAccountUpdated(LocalDateTime accountUpdated) {
        this.accountUpdated = accountUpdated;
    }

    public void setAccountVerified(Boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public void setVerificationTokenExpiry(LocalDateTime verificationTokenExpiry) {
        this.verificationTokenExpiry = verificationTokenExpiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }
}
