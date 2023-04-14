package com.spring.jwt.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @Column(columnDefinition="TEXT")
    private String name;
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
}
