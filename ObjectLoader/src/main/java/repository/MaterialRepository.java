package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.Material;

@Repository
@Transactional
public interface MaterialRepository extends CrudRepository<Material, String> {

}
