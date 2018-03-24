package com.onlineshop.format;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordFormat {

    @NotNull(message = "is required")
    @Size(min = 4, message = "minimum 4 characters")
    @Pattern(regexp="^[a-zA-Z0-9_-]*$", message = "wrong format")
    private String password;

    @NotNull(message = "is required")
    private String confirmPassword;

    public PasswordFormat() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
