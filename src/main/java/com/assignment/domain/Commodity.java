package com.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "commodities", indexes = {
        @Index(name = "FK_products_categories_idx", columnList = "category_id")
})

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commodity_id", nullable = false)
    private Integer commodity_id;

    @Column(name = "name", nullable = false, length = 155)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "status", nullable = false, length = 45)
    private Boolean status;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "commodity")
    List<Product> products;
}