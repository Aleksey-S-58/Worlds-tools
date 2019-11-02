package data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class is intended to save 3DO geometry in a database.
 * <br>Field bytes contains a 3DO geometry recorded in obj format.
 * @author Aleksey Shishkin
 *
 */
@Entity
@Table(schema = "ThreeDMap", name = "geometry")
public class Geometry extends AbstractObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2571612852608162581L;

	public Geometry() {
		
	}
	
	public Geometry(String name, byte[] bytes) {
		super();
		this.name = name;
		this.bytes = bytes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Geometry))
			return false;
		Geometry other = (Geometry) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
