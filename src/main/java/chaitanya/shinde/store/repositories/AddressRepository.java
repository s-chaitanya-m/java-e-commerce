package chaitanya.shinde.store.repositories;

import chaitanya.shinde.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
