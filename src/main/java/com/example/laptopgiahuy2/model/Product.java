package com.example.laptopgiahuy2.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ProductId;
    private String tensanpham;
    private int soluong;
    private String hinhanh;
    private Double gia;
    private String mota;
    private String cauhinh;
    private Boolean trangthai;
    private int baohanh;
    private String DanhMuc;
}
