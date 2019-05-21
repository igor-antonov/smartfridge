package ru.antonov.smartfridge.repository;

import org.springframework.data.repository.CrudRepository;
import ru.antonov.smartfridge.domain.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByUser_Login(String login);
    List<Product> findAll();
}
