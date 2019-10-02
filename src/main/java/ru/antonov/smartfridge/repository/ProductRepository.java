package ru.antonov.smartfridge.repository;

import org.springframework.data.repository.CrudRepository;
import ru.antonov.smartfridge.domain.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByUser_LoginOrderByBestBeforeDate(String login);
    List<Product> findAllByBestBeforeDateLessThan(LocalDate date);
}
