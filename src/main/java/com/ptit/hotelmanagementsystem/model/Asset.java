package com.ptit.hotelmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "assets")
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;
}
