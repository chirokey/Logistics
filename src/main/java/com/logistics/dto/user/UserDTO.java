package com.logistics.dto.user;

import com.logistics.dto.validation.annotation.PasswordMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@PasswordMatches
public abstract class UserDTO {
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String username;

    @NotEmpty
    @Size(min = 5, max = 40)
    private String password;

    @NotEmpty
    @Size(min = 5, max = 40)
    private String matchingPassword;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
