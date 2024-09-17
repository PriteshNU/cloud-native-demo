package com.cloudnative.webapi.model;

public record VerifyEmailPubSubPayload(
        String username,
        String firstname,
        String lastname,
        String verificationToken,
        Integer verificationTokenExpiry) {
}
