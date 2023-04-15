package com.spring.jwt.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String name;
    @Column(nullable = false)
    private double price;
    @Column(columnDefinition="TEXT")
    @Lob
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Timestamp createdAt;
}
