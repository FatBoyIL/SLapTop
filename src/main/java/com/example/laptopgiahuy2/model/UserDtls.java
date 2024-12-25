package com.example.laptopgiahuy2.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDtls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userName;
    private Integer sdt;
    private String email;
    private String diachi;
    private String password;
    private String profileImage;
    private String role;
    private Boolean hoatDong;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    private Boolean accountNonLocked;
    private Integer failedAttempts;
    private Date lockTime;
    private String resetToken;


}
