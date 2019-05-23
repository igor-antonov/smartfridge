package ru.antonov.smartfridge.repository;

import org.springframework.data.repository.CrudRepository;
import ru.antonov.smartfridge.domain.ProductDetails;

import java.util.Optional;

public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Long> {
    Optional<ProductDetails> findByName(String name);
}
