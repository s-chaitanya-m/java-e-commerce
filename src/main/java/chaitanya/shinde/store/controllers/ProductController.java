package chaitanya.shinde.store.controllers;
import chaitanya.shinde.store.dtos.ProductDto;
import chaitanya.shinde.store.entities.Product;
import chaitanya.shinde.store.mappers.ProductMapper;
import chaitanya.shinde.store.repositories.ProductRepository;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(required = false, name = "categoryId") Byte categoryId
    ) {
        List<Product> products;
        if (categoryId != null) {
            products =  productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }
}
