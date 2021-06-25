package com.example.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class AdminUserDTO {

    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Wrong Email format")
    private String userEmail;

    @Pattern(regexp = "^[A-Z][a-z]{1,20}$", message = "Wrong First Name format")
    @NotBlank(message = "First Name is mandatory")
    private String userFirstName;

    @Pattern(regexp = "^[A-Z][a-z]{1,20}$", message = "Wrong Last Name format")
    @NotBlank(message = "Last Name is mandatory")
    private String userLastName;

    private String roleList;

    public AdminUserDTO(@NotBlank(message = "Email is mandatory") @Email(message = "Wrong Email format") String userEmail, @Pattern(regexp = "^[A-Z][a-z]{1,20}$", message = "Wrong First Name format") @NotBlank(message = "First Name is mandatory") String userFirstName, @Pattern(regexp = "^[A-Z][a-z]{1,20}$", message = "Wrong Last Name format") @NotBlank(message = "Last Name is mandatory") String userLastName, String roleList) {
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getRoleList() {
        return roleList;
    }

    public void setRoleList(String roleList) {
        this.roleList = roleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminUserDTO)) return false;
        AdminUserDTO that = (AdminUserDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(userFirstName, that.userFirstName) &&
                Objects.equals(userLastName, that.userLastName) &&
                Objects.equals(roleList, that.roleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userEmail, userFirstName, userLastName, roleList);
    }
}
