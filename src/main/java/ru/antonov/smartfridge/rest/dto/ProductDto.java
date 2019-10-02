package ru.antonov.smartfridge.rest.dto;

import lombok.Data;
import ru.antonov.smartfridge.domain.Product;
import ru.antonov.smartfridge.domain.ProductDetails;
import ru.antonov.smartfridge.domain.User;

import java.time.LocalDate;

@Data
public class ProductDto {
    private long id;
    private ProductDetails productDetails;
    private User user;
    private LocalDate creationDate;
    private LocalDate bestBeforeDate;

    public ProductDto(long id, ProductDetails productDetails, User user, LocalDate creationDate, LocalDate bestBeforeDate) {
        this.id = id;
        this.productDetails = productDetails;
        this.user = user;
        this.creationDate = creationDate;
        this.bestBeforeDate = bestBeforeDate;
    }


    public static ProductDto toProductDto(Product product){
        return new ProductDto(product.getId(), product.getProductDetails(), product.getUser()
                            ,product.getCreationDate(), product.getBestBeforeDate());
    }
}
