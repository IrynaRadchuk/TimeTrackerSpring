package com.example.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class UserUpdateDto {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Wrong Email format")
    private String userEmail;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^.{6,}$", message = "Wrong Password format")
    private String userPassword;

    @Pattern(regexp = "^[A-Z][a-z]{1,20}$", message = "Wrong First Name format")
    @NotBlank(message = "First Name is mandatory")
    private String userFirstName;

    @Pattern(regexp = "^[A-Z][a-z]{1,20}$", message = "Wrong Last Name format")
    @NotBlank(message = "Last Name is mandatory")
    private String userLastName;

    @NotBlank(message = "Password is mandatory")
    private String userPasswordConfirm;

    public UserUpdateDto(String userEmail, String userPassword, String userFirstName, String userLastName, String userPasswordConfirm) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPasswordConfirm = userPasswordConfirm;
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

    public String getUserPasswordConfirm() {
        return userPasswordConfirm;
    }

    public void setUserPasswordConfirm(String userPasswordConfirm) {
        this.userPasswordConfirm = userPasswordConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserUpdateDto)) return false;
        UserUpdateDto that = (UserUpdateDto) o;
        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(userPassword, that.userPassword) &&
                Objects.equals(userFirstName, that.userFirstName) &&
                Objects.equals(userLastName, that.userLastName) &&
                Objects.equals(userPasswordConfirm, that.userPasswordConfirm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, userPassword, userFirstName, userLastName, userPasswordConfirm);
    }
}
