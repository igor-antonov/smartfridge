package ru.antonov.smartfridge.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product_details")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name", unique = true)
    private String name;
    @Column(name = "storage_period")
    private int storagePeriod;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "best_before_date")
    private LocalDate bestBeforeDate;
}
