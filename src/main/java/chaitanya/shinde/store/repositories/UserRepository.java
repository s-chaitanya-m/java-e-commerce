package chaitanya.shinde.store.repositories;

import chaitanya.shinde.store.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
