package ru.antonov.smartfridge.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_details_id")
    private ProductDetails productDetails;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Product(User user, ProductDetails productDetails){
        this.user = user;
        this.productDetails = productDetails;
    }
}/*
    CREATE TABLE IF NOT EXISTS PRODUCTS
        (
                ID SERIAL NOT NULL,
                PRODUCT_DETAILS_ID INTEGER,
                USER_ID INTEGER,
                CONSTRAINT PRODUCTS_PKEY PRIMARY KEY (id),
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
        FOREIGN KEY (PRODUCT_DETAILS_ID) REFERENCES PRODUCT_DETAILS(ID)
        );*/