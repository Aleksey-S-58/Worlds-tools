package data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This entity contains specific information about an object.
 * <br>Each object on a map can has several representation: one for each radius of view.
 * <br>The closer observer to the object the more detailed representation can be load.
 * @author Aleksey Shishkin
 *
 */
@Entity
@Table(name = "object_description", schema = "ThreeDMap")
public class Description implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7373492544873906892L;

	@Id
	@Column(name = "id")
	private long id;
	
	/**
	 * Foreign key to load (geometry, material and sprite).
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * Defines is an object a 3DO or a sprite.
	 * See {@link ObjectType}
	 */
	@Column(name = "object_type")
	private String type;
	
	/**
	 * The same objectId like in Location.objectId.
	 * In a case of partitioning this field should be used as a rule.
	 */
	@Column(name = "object_id")
	private long objectId;
	
	/**
	 * The radius of outer line of sight.
	 */
	@Column(name = "radius")
	private double radius;

	public Description() {}

	public Description(long id, String name, Enum<? extends TypeMarker> type, long objectId, double radius) {
		super();
		this.id = id;
		this.name = name;
		this.objectId = objectId;
		this.radius = radius;
		this.type = type.name();
	}

	public Description(long id, String name, String type, long objectId, double radius) {
		super();
		this.id = id;
		this.name = name;
		this.objectId = objectId;
		this.radius = radius;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(Enum<? extends TypeMarker> type) {
		this.type = type.name();
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof Description))
			return false;
		Description other = (Description) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
