package chaitanya.shinde.store.repositories;

import chaitanya.shinde.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
//import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Byte categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("SELECT p from Product p")
    List<Product> findAllWithCategory();
}
