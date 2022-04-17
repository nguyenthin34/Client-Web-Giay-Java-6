package com.assignment.model;

import com.assignment.domain.Color;
import com.assignment.domain.Commodity;
import com.assignment.domain.Orderdetail;
import com.assignment.domain.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class product {

    private Integer productId;

    private String size;

    private String color;

    private Integer quantity;

    private Double unitPrice;

    private Double price;

    private String commodityName;

    private Integer status;

    private String name;

    private String image;

    private String categoryName;

}
