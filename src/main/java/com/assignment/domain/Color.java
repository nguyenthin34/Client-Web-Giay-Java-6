package com.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "colors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id", nullable = false)
    private Integer color_id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @JsonIgnore
    @OneToMany(mappedBy = "color")
    List<Product> products;
}