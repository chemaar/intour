package es.uc3m.intour.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import es.uc3m.intour.to.POI;

public class FoursquareDAIImplTest {

	@Test
	public void test() {
		FoursquareDAIImpl foursquare = new FoursquareDAIImpl();
		POI poi = new POI();
		poi.setLat("51.568");
		poi.setLon("0.065532");
		//System.out.println(foursquare.searchPOISAround(poi));
	}

}
