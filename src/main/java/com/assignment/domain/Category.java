package com.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer category_id;

    @Column(name = "name", nullable = false, length = 105)
    private String name;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Commodity> commodities;

}