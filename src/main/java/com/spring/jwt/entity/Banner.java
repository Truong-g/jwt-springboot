package com.spring.jwt.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "banners")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

}
