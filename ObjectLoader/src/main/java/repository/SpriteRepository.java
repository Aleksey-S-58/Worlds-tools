package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.Sprite;

@Repository
@Transactional
public interface SpriteRepository extends CrudRepository<Sprite, String> {

}
