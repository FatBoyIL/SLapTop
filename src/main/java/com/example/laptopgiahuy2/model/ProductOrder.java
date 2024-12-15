package com.example.laptopgiahuy2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderId;
    private Date orderDate;
    private Integer price;
    private Integer quantity;
    private String status;
    private String paymentType;
    @ManyToOne
    private Product product;
    @ManyToOne
    private UserDtls userDtls;
    @OneToOne(cascade = CascadeType.ALL)
    private OrderAddress orderAddress;

}
