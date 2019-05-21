package ru.antonov.smartfridge.repository;

import org.springframework.data.repository.CrudRepository;
import ru.antonov.smartfridge.domain.ProductDetails;

public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Long> {
    ProductDetails findByName(String name);
}
