package data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class is intended to save 3DO material in a database.
 * <br>Field bytes contains a 3DO material recorded in mtl format.
 * @author Aleksey Shishkin
 *
 */
@Entity
@Table(schema = "ThreeDMap", name = "material")
public class Material extends AbstractObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4076811390065593908L;
	
	public Material() {
		
	}

	public Material(String name, byte[] bytes) {
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
		if (!(obj instanceof Material))
			return false;
		Material other = (Material) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
