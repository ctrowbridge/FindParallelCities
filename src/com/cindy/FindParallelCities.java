package com.cindy;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Driver which uses CityLocator class to find a list of cities close
 * to the input latitude.
 * 
 * @author Cindy
 */
public class FindParallelCities {

	public static void main(String[] args) {

		System.out.println("<><><><><> FindParellelCities <><><><><>");
		System.setProperty("log4j.configurationFile", "log4j2.xml");

		// (38.833882, -104.821363)
		float inputLat = 38.833882f;
		float inputlongitude = -104.821363f;

		CityLocator cl = new CityLocator();

		List<Location> locations = cl.findCities(inputLat);

		for (Location loc : locations) {
			System.out.println(
					"City = " + loc.getCity() + ", country = " + loc.getCountry() + ", latitude = " + loc.getLat());
		}

		System.out.println("Done!");
	}

}
