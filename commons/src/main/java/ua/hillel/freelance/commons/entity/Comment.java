package ua.hillel.freelance.commons.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Comment {
    private int id;
    private String message;
    private String username;
    private String commentDate;
}
