package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Setter
@Getter
@Entity
@Table(name = "museums")
public class Museum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String openingHours;

    private String address;

    @Column(nullable = true, length = 64)
    private String imageURL;

    private ArrayList<Long> exhibitions;
    private ArrayList<String> tags;
}
