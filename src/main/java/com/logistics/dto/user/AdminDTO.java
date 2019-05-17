package com.logistics.dto.user;

import com.logistics.dto.validation.annotation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AdminDTO extends UserDTO {
    @NotEmpty
    @Size(min = 5, max = 50)
    @ValidEmail
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
