package com.springboot.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "exhibitions")
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long museumId;

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;

    @Column(nullable = true, length = 64)
    private String fileName;

    private ArrayList<Tag> tags;
}
