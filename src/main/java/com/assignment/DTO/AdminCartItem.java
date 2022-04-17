package com.assignment.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCartItem {
    private Integer id;
    private Integer commodityId;
    private String name;
    private String image;
    private Double unitPrice;
    private Integer quantity;
    private Integer colorId;
    private Integer sizeId;
}
