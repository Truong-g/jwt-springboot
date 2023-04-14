package com.spring.jwt.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long id;

    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;


    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
}
