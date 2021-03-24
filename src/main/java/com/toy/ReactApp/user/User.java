package com.toy.ReactApp.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.toy.ReactApp.shared.Views;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "{reactApp.validation.constraints.username.NotNull.message}")
    @Size(min = 4,max = 255)
    @UniqueUsername(message = "{reactApp.validation.constraints.uniqueUsername.message}")
    @JsonView(Views.Base.class)
    private String username;

    @NotNull
    @Size(min = 4,max = 255)
    @JsonView(Views.Base.class)
    private String displayname;

    @NotNull
    @Size(min = 8, max = 255)
    @Pattern( regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    @JsonView(Views.Base.class)
    private String image;
}
