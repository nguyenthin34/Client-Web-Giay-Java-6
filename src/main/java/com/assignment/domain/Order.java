package com.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "FK_order_user_idx", columnList = "creator")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer order_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator", nullable = false)
    private User user;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "status", nullable = false)
    private Integer status;


    @JsonIgnore
    @OneToMany(mappedBy = "order")
    List<Orderdetail> orderdetails;

}