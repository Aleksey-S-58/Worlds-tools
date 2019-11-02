package dto;

import java.io.Serializable;

import data.ObjectType;
import service.Geolocation;

/**
 * This DTO is used to transmit data about object's location to a front-end.
 * @author Aleksey Shishkin
 *
 */
public class ThreeDObject implements Serializable, Geolocation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -680996057731479287L;

	private String name;
	
	private ObjectType type;

	private double latitude;
	
	private double longitude;

	private double hight;

	private double alphaX;

	private double alphaY;

	private double alphaZ;
	
	public ThreeDObject() {
		
	}

	public ThreeDObject(String name, ObjectType type, double latitude, double longitude, double hight, double alphaX, double alphaY, double alphaZ) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.hight = hight;
		this.alphaX = alphaX;
		this.alphaY = alphaY;
		this.alphaZ = alphaZ;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ObjectType getType() {
		return type;
	}
	
	public void setType(ObjectType type) {
		this.type = type;
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

}
