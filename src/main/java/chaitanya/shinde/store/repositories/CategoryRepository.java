package chaitanya.shinde.store.repositories;

import chaitanya.shinde.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}
