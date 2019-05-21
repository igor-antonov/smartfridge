package ru.antonov.smartfridge.rest.dto;

import lombok.Data;
import ru.antonov.smartfridge.domain.Product;
import ru.antonov.smartfridge.domain.ProductDetails;
import ru.antonov.smartfridge.domain.User;

@Data
public class ProductDto {
    private long id;
    private ProductDetails productDetails;
    private User user;

    public ProductDto(long id, ProductDetails productDetails, User user) {
        this.id = id;
        this.productDetails = productDetails;
        this.user = user;
    }


    public static ProductDto toProductDto(Product product){
        return new ProductDto(product.getId(), product.getProductDetails(), product.getUser());
    }
}
