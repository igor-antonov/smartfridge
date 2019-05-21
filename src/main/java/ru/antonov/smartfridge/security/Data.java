package ru.antonov.smartfridge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.antonov.smartfridge.domain.Product;
import ru.antonov.smartfridge.domain.ProductDetails;
import ru.antonov.smartfridge.domain.User;
import ru.antonov.smartfridge.repository.ProductDetailsRepository;
import ru.antonov.smartfridge.repository.ProductRepository;
import ru.antonov.smartfridge.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class Data {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductDetailsRepository productDetailsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Data(UserRepository userRepository, ProductRepository productRepository, ProductDetailsRepository productDetailsRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productDetailsRepository = productDetailsRepository;
    }

    @PostConstruct
    void init(){
        if (!userRepository.findByLogin("1").isPresent())
        {
            userRepository.save(new User("1", "fio", passwordEncoder.encode("1")));
        }
        ProductDetails productDetails = new ProductDetails();
        productDetails.setName("хлеб");
        productDetails.setStoragePeriod(5);
        productDetails.setBestBeforeDate(LocalDate.now());
        //productRepository.save(productDetails);
        //ProductDetails product2 = new ProductDetails();
        //product2.setName("молоко");
        //product2.setStoragePeriod(10);
        //productDetailsRepository.save(productDetails);
        productRepository.save(new Product(userRepository.findByLogin("123").get(),
                productDetailsRepository.findByName("хлеб")));


    }
}
