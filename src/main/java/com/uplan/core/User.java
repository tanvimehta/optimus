package com.uplan.core;

import javax.persistence.*;
/**
 * Created by tanvimehta on 15-01-16.
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "com.uplan.core.User.verifyUser",
                query = "SELECT user FROM User user where email = :email and password= :password and valid= 1"
        ),
        @NamedQuery(
                name = "com.uplan.core.User.verifyUserUsingEmail",
                query = "SELECT user FROM User user where email = :email"
        ),
        @NamedQuery(
                name = "com.uplan.core.User.getUserById",
                query = "SELECT user FROM User user where user_id = :user_id"
        ),
        @NamedQuery(
                name = "com.uplan.core.User.setRegId",
                query = "UPDATE User user SET reg_id = :reg_id where user_id = :user_id"
        ),
        @NamedQuery(
                name = "com.uplan.core.User.deleteUser",
                query = "DELETE FROM User where user_id = :user_id"
        ),
        @NamedQuery(
        name = "com.uplan.core.User.setValid",
        query = "UPDATE User user SET valid = :valid where user_id = :user_id"
)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(name="first_name", nullable = false)
    private String first_name;

    @Column(name="last_name", nullable = false)
    private String last_name;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="token", nullable = false)
    private String token;

    @Column(name="valid", nullable = true)
    private boolean valid;

    @Column(name="reg_id", nullable = true)
    private String reg_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() { return last_name; }

    public void setLastName(String lastName) { this.last_name = lastName; }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}

