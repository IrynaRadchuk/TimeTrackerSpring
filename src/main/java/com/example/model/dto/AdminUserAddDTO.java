package com.example.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class AdminUserAddDTO {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Wrong Email format")
    private String userAddEmail;

    @Pattern(regexp = "^[A-Z][a-z]{1,20}$", message = "Wrong First Name format")
    @NotBlank(message = "First Name is mandatory")
    private String userAddFirstName;

    @Pattern(regexp = "^[A-Z][a-z]{1,20}$", message = "Wrong Last Name format")
    @NotBlank(message = "Last Name is mandatory")
    private String userAddLastName;

    private String roleAdd;

    public AdminUserAddDTO(String userAddEmail, String userAddFirstName, String userAddLastName, String roleAdd) {
        this.userAddEmail = userAddEmail;
        this.userAddFirstName = userAddFirstName;
        this.userAddLastName = userAddLastName;
        this.roleAdd = roleAdd;
    }

    public String getUserAddEmail() {
        return userAddEmail;
    }

    public void setUserAddEmail(String userAddEmail) {
        this.userAddEmail = userAddEmail;
    }

    public String getUserAddFirstName() {
        return userAddFirstName;
    }

    public void setUserAddFirstName(String userAddFirstName) {
        this.userAddFirstName = userAddFirstName;
    }

    public String getUserAddLastName() {
        return userAddLastName;
    }

    public void setUserAddLastName(String userAddLastName) {
        this.userAddLastName = userAddLastName;
    }

    public String getRoleAdd() {
        return roleAdd;
    }

    public void setRoleAdd(String roleAdd) {
        this.roleAdd = roleAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminUserAddDTO)) return false;
        AdminUserAddDTO that = (AdminUserAddDTO) o;
        return Objects.equals(userAddEmail, that.userAddEmail) &&
                Objects.equals(userAddFirstName, that.userAddFirstName) &&
                Objects.equals(userAddLastName, that.userAddLastName) &&
                Objects.equals(roleAdd, that.roleAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAddEmail, userAddFirstName, userAddLastName, roleAdd);
    }
}
