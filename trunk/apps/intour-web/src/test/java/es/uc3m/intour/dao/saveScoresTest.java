package es.uc3m.intour.dao;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import es.uc3m.intour.to.POI;

public class saveScoresTest {

	
	public static void main(String []args) {
		MahoutPOIDAOImpl rp = new MahoutPOIDAOImpl();
		List <POI> pois = new LinkedList<POI>();
		//Puntos de ejemplo
		POI poi1 = new POI();
		poi1.setLat("51.568517");
		poi1.setLon("0.069289");
		POI poi2 = new POI();
		poi2.setLat("51.567598");
		poi2.setLon("0.064993");
		POI poi3 = new POI();
		poi3.setLat("51.574401");
		poi3.setLon("0.067501");
		POI poi4 = new POI();
		poi4.setLat("51.559813");
		poi4.setLon("0.069040");
		POI poi5 = new POI();
		poi5.setLat("24.056894");
		poi5.setLon("40.235672");
		POI poi6 = new POI();
		poi6.setLat("35.678945");
		poi6.setLon("48.234758");

		pois.add(poi1);
		pois.add(poi2);
		pois.add(poi3);
		pois.add(poi4);
		pois.add(poi5);
		pois.add(poi6);
		rp.saveScores(pois, "3");
	}

}
