package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_email")
    @NotBlank(message = "Email is mandatory")
    @Email (message = "Wrong Email format")
    private String userEmail;
    @Column(name = "user_password")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,20}$", message = "Wrong Password format")
    @NotBlank(message = "Password is mandatory")
    private String userPassword;
    @Column(name = "user_first_name")
    @Pattern(regexp="^[A-Z][a-z]{2,20}$", message = "Wrong First Name format")
    @NotBlank(message = "First Name is mandatory")
    private String userFirstName;
    @Column(name = "user_last_name")
    @Pattern(regexp="^[A-Z][a-z]{2,20}$", message = "Wrong Last Name format")
    @NotBlank(message = "Last Name is mandatory")
    private String userLastName;
    @Column(name = "role_id")
    private int roleId;

    public User() {
    }

    public User(String userEmail, String userPassword, String userFirstName, String userLastName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.roleId = 1;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return roleId == user.roleId &&
                Objects.equals(id, user.id) &&
                Objects.equals(userEmail, user.userEmail) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(userFirstName, user.userFirstName) &&
                Objects.equals(userLastName, user.userLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userEmail, userPassword, userFirstName, userLastName, roleId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
