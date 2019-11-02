package data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import service.Geolocation;

/**
 * This entity represents an object location on a map.
 * In case of horizontal partitioning the fields: latitude and longitude 
 * should be used as a rule.
 * Angles alphaX, alphaY, alphaZ are measured in radians.
 * @author Aleksey Shishkin
 *
 */
@Entity
@Table(name = "object_location", schema = "ThreeDMap")
public class Location implements Serializable, Geolocation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5562538762667170742L;

	@Id
	@Column(name = "id")
	private long id;
	
	/**
	 * Foreign key that binds several descriptions to the location.
	 */
	@Column(name = "object_id")
	private long objectId;

	@Column(name = "latitude")
	private double latitude;
	
	@Column(name = "longitude")
	private double longitude;
	
	@Column(name = "hight")
	private double hight;
	
	@Column(name = "alphaX")
	private double alphaX;
	
	@Column(name = "alphaY")
	private double alphaY;
	
	@Column(name = "alphaZ")
	private double alphaZ;
	
	public Location() {
		
	}

	public Location(long id, long objectId, double latitude, double longitude, double hight, double alphaX, double alphaY, double alphaZ) {
		super();
		this.id = id;
		this.objectId = objectId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.hight = hight;
		this.alphaX = alphaX;
		this.alphaY = alphaY;
		this.alphaZ = alphaZ;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longetude) {
		this.longitude = longetude;
	}
	
	public double getHight() {
		return hight;
	}
	
	public void setHight(double hight) {
		this.hight = hight;
	}

	public double getAlphaX() {
		return alphaX;
	}

	public void setAlphaX(double alphaX) {
		this.alphaX = alphaX;
	}

	public double getAlphaY() {
		return alphaY;
	}

	public void setAlphaY(double alphaY) {
		this.alphaY = alphaY;
	}

	public double getAlphaZ() {
		return alphaZ;
	}

	public void setAlphaZ(double alphaZ) {
		this.alphaZ = alphaZ;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}	

	public long getObjectId() {
		return objectId;
	}
	
	public void setObjectId(long id) {
		this.objectId = id;
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
		if (! (obj instanceof Location))
			return false;
		Location other = (Location) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", objectId=" + objectId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", hight=" + hight + ", alphaX=" + alphaX + ", alphaY=" + alphaY + ", alphaZ=" + alphaZ + "]";
	}
	
}
