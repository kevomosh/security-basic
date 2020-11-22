package com.kakuom.finalshop.views;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterView {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank(message = "email is reqired")
    @Size(max = 60)
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 60)
    private String password;

    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
