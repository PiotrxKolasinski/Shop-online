package com.onlineshop.format;

import javax.validation.constraints.*;

public class CrmUser extends PasswordFormat {

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Pattern(regexp="^[a-zA-Z0-9_-]*$", message = "wrong format")
    private String username;


    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Email(message = "wrong format")
    private String email;

    public CrmUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CrmUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
