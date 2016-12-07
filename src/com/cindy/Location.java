package com.cindy;

/**
 * Encapsulates data for one location.
 * 
 * @author Cindy
 *
 */
public class Location {

	private float lat;
	private float lon;
	private float population;
	private String country;
	private String city;
	private String countryCode;
	
	public Location() {
		lat = 0.0f;
		lon = 0.0f;
		population = 0.0f;
		country = "";
		city = "";
		countryCode = "";
	}
	
	public Location(float lat, float lon, float population, String country, String city, String countryCode) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.population = population;
		this.country = country;
		this.city = city;
		this.countryCode = countryCode;
	}
	
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public float getPopulation() {
		return population;
	}
	public void setPopulation(float population) {
		this.population = population;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
