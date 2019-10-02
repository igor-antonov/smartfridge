package ru.antonov.smartfridge.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.antonov.smartfridge.domain.Product;
import ru.antonov.smartfridge.exception.DataNotFoundException;
import ru.antonov.smartfridge.rest.dto.ProductDto;
import ru.antonov.smartfridge.service.ProductService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> findAll(Principal principal) {
        return productService.findAllByUser(principal.getName()).stream()
                .map(ProductDto::toProductDto).collect(Collectors.toList());
    }

    @PostMapping
    public Product save(@RequestBody Product product, Principal principal) throws DataNotFoundException{
        return productService.save(product, principal.getName());
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long productId) {
        try {
            productService.deleteById(productId);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (DataNotFoundException e){
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public Product edit(@PathVariable("id") Long productId, @RequestBody Product product) throws DataNotFoundException {
        try {
            return productService.updateById(productId, product);
        }
        catch (DataNotFoundException e) {
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DataNotFoundException(e);
        }
    }
}
