package com.example.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRegisterDTO {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Wrong Email format")
    private String userEmail;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp="^.{6,}$", message = "Wrong Password format")
    private String userPassword;
    @Pattern(regexp="^[A-Z][a-z]{1,20}$", message = "Wrong First Name format")
    @NotBlank(message = "First Name is mandatory")
    private String userFirstName;
    @Pattern(regexp="^[A-Z][a-z]{1,20}$", message = "Wrong Last Name format")
    @NotBlank(message = "Last Name is mandatory")
    private String userLastName;

    public UserRegisterDTO(String userEmail, String userPassword, String userFirstName, String userLastName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }
}
