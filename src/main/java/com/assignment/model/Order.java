package com.assignment.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Order {



    private Timestamp createDate;
    private String user;
    private Double totalAmount;
    private String phone;
    private String address;
    private String name;
    private String email;
    private Integer status;
    Object orderDetails;
}
