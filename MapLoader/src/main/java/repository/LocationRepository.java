package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.Location;


/**
 * This interface is intended to load objects locations.
 * @author Aleksey Shishkin
 *
 */
@Repository
@Transactional
public interface LocationRepository extends CrudRepository<Location, Long> {

	@Query("SELECT o FROM Location o WHERE o.longitude > :minLongitude AND o.longitude < :maxLongitude AND o.latitude BETWEEN :minLatitude AND :maxLatitude")
	public List<Location> getObjectsInLocation(
			@Param("minLatitude") double minLatitude, 
			@Param("maxLatitude") double maxLatitude, 
			@Param("minLongitude") double minLongitude, 
			@Param("maxLongitude") double maxLongitude);

	//TODO rewrite via JPQL or HQL not to be nailed to postgresql
	@Query(value = "select nextval('ThreeDMap.object_id_sequence')", nativeQuery = true)
	public long getNextObjectId();
}
