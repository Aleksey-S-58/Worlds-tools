package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.Geometry;

@Repository
@Transactional
public interface GeometryRepository extends CrudRepository<Geometry, String> {

}
