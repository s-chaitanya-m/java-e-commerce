package chaitanya.shinde.store.mappers;

import chaitanya.shinde.store.dtos.ProductDto;
import chaitanya.shinde.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);
    Product toEntity(ProductDto request);
    @Mapping(target = "id", ignore = true)
    void update(ProductDto productDto,@MappingTarget Product product);
}
