package chaitanya.shinde.store.repositories;

import chaitanya.shinde.store.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
