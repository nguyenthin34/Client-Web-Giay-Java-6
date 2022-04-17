package com.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "FK_product_commodity_idx", columnList = "commodity_id"),
        @Index(name = "FK_product_size_idx", columnList = "size_id"),
        @Index(name = "FK_product_color_idx", columnList = "color_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer product_id;

    @ManyToOne()
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @ManyToOne()
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne()
    @JoinColumn(name = "commodity_id", nullable = false)
    private Commodity commodity;

    @Column(name = "status", nullable = false)
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<Orderdetail> orderdetails;
}