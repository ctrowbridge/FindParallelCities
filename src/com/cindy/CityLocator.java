package com.cindy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides ability to find cities close to a given latitude.
 * 
 * @author Cindy
 */
public class CityLocator {

	private float latitudeAllowance = 0.2f;
	String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	String dbName = "locations";
	String connectionURL = "jdbc:derby:" + dbName + ";create=true";
	Connection conn = null;

	private static final Logger logger = LogManager.getLogger("CityLocator");
	
	List<Location> locations;

	public CityLocator() {
		locations = readLocations();
	}

	
	public float getLatitudeAllowance() {
		return latitudeAllowance;
	}

	public void setLatitudeAllowance(float latitudeAllowance) {
		this.latitudeAllowance = latitudeAllowance;
	}


	/**
	 * Returns all cities close to the input latitude;
	 * 
	 * @param lat
	 * @return
	 */
	List<Location> findCities(float lat) {

		logger.debug("CityLocator::findCities:  lat = " + lat);
		
		List<Location> foundLocations = new ArrayList<Location>();
		
		int countFound = 0;
		for (Location loc : locations) {
			if (Math.abs(loc.getLat() - lat) < latitudeAllowance) {
				countFound++;
				foundLocations.add(loc);
			}
		}

		logger.info("Number of cities = " + countFound);
		return foundLocations;
	}

	/**
	 * Reads in all locations from the database.
	 * 
	 * @return An array of locations in the database
	 */
	private List<Location> readLocations() {
		
		logger.debug("CityLocation::readLocations:");
		
		openDatabase();
		
		locations = new ArrayList<Location>();

		try {
			
			Statement s;
			s = conn.createStatement();
			ResultSet results = s.executeQuery("select * from locations");
			logger.debug(results.getCursorName());

			int count = 0;
			while (results.next()) {
				/* city varchar(200),      1
    			country varchar(100),      2
    			country_code varchar(5),   3
    			latitude float,            4
    			longitude float,           5
    			population float           6
				*/
				
				count++;
				logger.debug(count + " City = " + results.getString(1));
				
				Location loc = new Location();
				loc.setCity(results.getString(1));
				loc.setCountry(results.getString(2));
				loc.setCountryCode(results.getString(3));
				loc.setLat(results.getFloat(4));
				loc.setLon(results.getFloat(5));
				loc.setPopulation(results.getFloat(6));
				
				locations.add(loc);
				
			}
			results.close();
			
			logger.debug("CityLocations::readLocations:  " + count + " cities loaded");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeDatabase();
		shutdownDerby();

		return locations;
	}

	private void openDatabase() {

		logger.debug("CityLocator::openDatabase:");
		try {
			conn = DriverManager.getConnection(connectionURL);
			logger.info("Connected to database \"" + dbName + "\"");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void closeDatabase() {
		logger.debug("CityLocator::closeDatabase:");
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void shutdownDerby() {
		if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
			boolean gotSQLExc = false;
			try {
				DriverManager.getConnection("jdbc:derby:;shutdown=true");
			} catch (SQLException se) {
				if (se.getSQLState().equals("XJ015")) {
					gotSQLExc = true;
				}
			}
			if (!gotSQLExc) {
				logger.warn("CityLocator::shutdownDerby:  Database did not shut down normally");
			} else {
				logger.info("CityLocator::shutdownDerby:  Database shut down normally");
			}
		}
	}
}
