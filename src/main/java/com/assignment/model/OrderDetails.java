package com.assignment.model;

import com.assignment.domain.Order;
import com.assignment.domain.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class OrderDetails {

    private Double price;
    private Integer quantity;
    private Integer product_id;
}
