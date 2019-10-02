package ru.antonov.smartfridge;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ru.antonov.smartfridge.domain.Product;
import ru.antonov.smartfridge.domain.ProductDetails;
import ru.antonov.smartfridge.domain.User;
import ru.antonov.smartfridge.repository.ProductDetailsRepository;
import ru.antonov.smartfridge.repository.ProductRepository;
import ru.antonov.smartfridge.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collections;

@RunWith(SpringRunner.class)
@ComponentScan
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDetailsRepository detailsRepository;
    @Autowired
    UserRepository userRepository;

    private User user;
    private ProductDetails details;
    private Product product;

    @Before
    public void prepare(){
        user = userRepository.save(
                new User("login", "fio"
                        , "fio@mail.ru", "pass"));
        details = new ProductDetails();
        details.setName("хлеб");
        details.setStoragePeriod(5);
        details = detailsRepository.save(details);
        product = productRepository.save(
                new Product(user, details
                        , LocalDate.now().minusDays(5), LocalDate.of(2019,5,24)));
    }

    @Test
    public void findAllByBestBeforeDateGreaterThanTest(){
        Assertions.assertThat(
                productRepository.findAllByBestBeforeDateLessThan(LocalDate.now().plusDays(2)))
                    .isEqualTo(Collections.singletonList(product));
    }
}
