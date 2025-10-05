package chaitanya.shinde.store.controllers;
import chaitanya.shinde.store.dtos.ProductDto;
import chaitanya.shinde.store.entities.Product;
import chaitanya.shinde.store.mappers.ProductMapper;
import chaitanya.shinde.store.repositories.CategoryRepository;
import chaitanya.shinde.store.repositories.ProductRepository;
import lombok.*;
import org.apache.coyote.Response;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @GetMapping("/test-header")
    public String testHeader (
            @RequestHeader(required = false, name = "x-auth-token") String authToken
    ) {
        System.out.println(authToken);
        return "header attached with x-auth-token " ;
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable(name = "id") Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder
    ){
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) return ResponseEntity.badRequest().build();

        var product = productMapper.toEntity(productDto);
        product.setCategory(category);

        productRepository.save(product);

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(productMapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto productDto
    ) {

        var product = productRepository.findById(id).orElse(null);
        if (product == null) return ResponseEntity.notFound().build();

        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) return ResponseEntity.badRequest().build();

        productMapper.update(productDto, product);
        product.setCategory(category);
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable(name = "id") Long id
    ) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) return ResponseEntity.notFound().build();

        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}
