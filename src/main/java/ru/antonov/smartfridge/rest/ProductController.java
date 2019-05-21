package ru.antonov.smartfridge.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.antonov.smartfridge.exception.DataNotFoundException;
import ru.antonov.smartfridge.repository.ProductRepository;
import ru.antonov.smartfridge.rest.dto.ProductDto;
import ru.antonov.smartfridge.service.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public List<ProductDto> findAll(Principal principal) throws DataNotFoundException {
        //return productRepository.findByUser_Login(principal.getName());
        //return productRepository.findAll().stream()
        //        .map(ProductDto::toProductDto).collect(Collectors.toList());
        log.info("findAll" + principal.toString());
        return productService.findAll().stream()
                .map(ProductDto::toProductDto).collect(Collectors.toList());
    }
}
