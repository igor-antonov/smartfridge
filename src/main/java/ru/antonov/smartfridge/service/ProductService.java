package ru.antonov.smartfridge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.antonov.smartfridge.domain.Product;
import ru.antonov.smartfridge.domain.ProductDetails;
import ru.antonov.smartfridge.domain.User;
import ru.antonov.smartfridge.exception.DataNotFoundException;
import ru.antonov.smartfridge.repository.ProductDetailsRepository;
import ru.antonov.smartfridge.repository.ProductRepository;
import ru.antonov.smartfridge.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final UserRepository userRepository;
    private final ProductDetailsRepository detailsRepository;
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository repository, UserRepository userRepository, ProductDetailsRepository detailsRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
    }

    private Product getById(Long id) throws DataNotFoundException {
        Optional<Product> productOpt = repository.findById(id);
        if (!productOpt.isPresent()){
            throw new DataNotFoundException(String.format("Продукт по идентификатору %d не найден", id));
        }
        else {
            return productOpt.get();
        }
    }

    public List<Product> findAllByUser(String login) {
        return repository.findByUser_LoginOrderByBestBeforeDate(login);
    }

    List<Product> findExpiresSoon() {
        return repository.findAllByBestBeforeDateLessThan(LocalDate.now().plusDays(2));
    }

    public Product updateById(Long productId, Product product) throws DataNotFoundException {
        Product productOld = getById(productId);
        product.setId(productOld.getId());
        return repository.save(product);
    }

    public void deleteById(Long id) throws DataNotFoundException {
        getById(id);
        repository.deleteById(id);
    }

    public Product save(Product product, String login) throws DataNotFoundException {
        Optional<User> userOpt = userRepository.findByLogin(login);
        Optional<ProductDetails> detailsOpt
                = detailsRepository.findByName(product.getProductDetails().getName());
        if (detailsOpt.isPresent()){
            product.getProductDetails().setId(detailsOpt.get().getId());
            if (!product.getProductDetails().getName().equals(detailsOpt.get().getName())){
                log.info(String.format("Детали для продукта %s изменены",
                        product.getProductDetails().getName()));
                detailsRepository.save(product.getProductDetails());
            }
        }
        else {
            product.getProductDetails().setId(detailsRepository.save(product.getProductDetails()).getId());
        }
        if (userOpt.isPresent()){
            product.setUser(userOpt.get());
            return repository.save(product);
        }
        else {
            throw new DataNotFoundException(String.format("Пользователь %s не найден", login));
        }
    }
}
