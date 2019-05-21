package ru.antonov.smartfridge.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.antonov.smartfridge.domain.Product;
import ru.antonov.smartfridge.exception.DataNotFoundException;
import ru.antonov.smartfridge.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    //@HystrixCommand(fallbackMethod = "fallbackBook", groupKey = "BookService", commandKey = "getById")
    private Product getById(Long id) throws DataNotFoundException {
        Optional<Product> productOpt = repository.findById(id);
        if (!productOpt.isPresent()){
            throw new DataNotFoundException(String.format("Продукт по идентификатору %d не найден", id));
        }
        else {
            return productOpt.get();
        }
    }

    @HystrixCommand(fallbackMethod = "fallbackAll", groupKey = "ProductService", commandKey = "findAll")
    public List<Product> findAll() {
        return repository.findAll();
    }

    //@HystrixCommand(fallbackMethod = "fallbackId", groupKey = "BookService", commandKey = "updateById")
    public void updateById(Long productId, Product product) throws DataNotFoundException {
        Product productOld = getById(productId);
        product.setId(productOld.getId());
        repository.save(product);
    }

    //@HystrixCommand(fallbackMethod = "fallbackId", groupKey = "BookService", commandKey = "deleteById")
    public void deleteById(Long id) throws DataNotFoundException {
        getById(id);
        repository.deleteById(id);
    }

    //@HystrixCommand(fallbackMethod = "fallbackBook", groupKey = "BookService", commandKey = "add")
    public Product save(Product product) {
        //if (product.getProductDetails().)
        return repository.save(product);
    }

    public List<Product> fallbackAll() throws DataNotFoundException {
        String errMessage = "Что-то пошло не так. Обратитесь к администратору";
        log.error(errMessage);
        throw new DataNotFoundException(errMessage);
    }
}
