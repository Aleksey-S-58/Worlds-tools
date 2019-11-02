package data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class is intended to save a sprite in a database.
 * <br>Field bytes contains an image of a sprite recorded either in png or in jpg format.
 * @author Aleksey Shishkin
 *
 */
@Entity
@Table(schema = "ThreeDMap", name = "sprite")
public class Sprite extends AbstractObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8030005719261010023L;
	
	public Sprite() {
		
	}

	public Sprite(String name, byte[] bytes) {
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
		if (!(obj instanceof Sprite))
			return false;
		Sprite other = (Sprite) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
