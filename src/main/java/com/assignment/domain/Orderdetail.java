package com.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orderdetails", indexes = {
        @Index(name = "FK_product_orderdetail_idx", columnList = "product_id"),
        @Index(name = "FK_order_orderdetail_idx", columnList = "order_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orderdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}