package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.Description;

/**
 * This repository allows to load descriptions by objectId.
 * @author Aleksey Shishkin
 *
 */
@Repository
@Transactional
public interface DescriptionRepository extends CrudRepository<Description, Long> {

	@Query("SELECT d FROM Description d WHERE d.objectId IN :list")
	public List<Description> getWhichInList(@Param("list") List<Long> identifiers);
	
	
}
