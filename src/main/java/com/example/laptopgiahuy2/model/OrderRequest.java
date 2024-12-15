package com.example.laptopgiahuy2.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class OrderRequest {
    private Integer id;
    private String guestName;
    private String email;
    private String sdt;
    private String address;
    private String ghichu;
    private String paymentType;
}
