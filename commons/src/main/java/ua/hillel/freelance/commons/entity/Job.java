package ua.hillel.freelance.commons.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Job {
    private int id;
    private String title;
    private String description;
    private double price;
    private String user;
    private int noOfComments;
}
