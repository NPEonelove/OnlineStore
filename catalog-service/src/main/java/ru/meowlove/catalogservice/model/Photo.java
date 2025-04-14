package ru.meowlove.catalogservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_seq")
    @SequenceGenerator(name = "photo_seq", sequenceName = "photo_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "photoLink")
    private String photoLink;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;



}
