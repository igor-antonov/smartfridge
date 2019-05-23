package ru.antonov.smartfridge.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_details_id")
    private ProductDetails productDetails;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "best_before_date")
    private LocalDate bestBeforeDate;

    public Product(User user, ProductDetails productDetails, LocalDate creationDate, LocalDate bestBeforeDate){
        this.user = user;
        this.productDetails = productDetails;
        this.creationDate = creationDate;
        this.bestBeforeDate = bestBeforeDate;
    }
}