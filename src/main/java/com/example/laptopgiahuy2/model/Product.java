package com.example.laptopgiahuy2.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
    private int productId;
    private String tensanpham;
    private int soluong;
    private String hinhanh;
    private int gia;
    private String mota;
    private String cauhinh;
    private int baohanh;
    private String danhMuc;
    private int sale;
    private int giaSale;
    private Boolean trangthai;
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private Set<Comment> comment = new HashSet<>();
}
