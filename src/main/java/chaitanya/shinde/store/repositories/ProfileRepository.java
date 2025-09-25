package chaitanya.shinde.store.repositories;

import chaitanya.shinde.store.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
