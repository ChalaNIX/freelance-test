package ua.hillel.freelance.commons.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String name;
    private String lastname;
    private String password;
    private String confirmPassword;
}
