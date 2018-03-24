package com.onlineshop.format;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SupportFormat {

    @NotBlank(message="is required")
    @Email(message="wrong email!")
    private String email;

    @NotBlank(message="is required")
    @Size(min=6, max=50, message = "characters between: 6-50")
    private String subject;

    @NotBlank(message="is required")
    @Size(min=10, message = "write min. 10 characters")
    private String message;

    public SupportFormat() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
