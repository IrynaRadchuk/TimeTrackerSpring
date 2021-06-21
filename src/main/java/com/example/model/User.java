package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


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

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;


    public User() {
    }

    public User(String userEmail, String userPassword, String userFirstName, String userLastName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.role = new Role(1L,RoleName.USER);
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
