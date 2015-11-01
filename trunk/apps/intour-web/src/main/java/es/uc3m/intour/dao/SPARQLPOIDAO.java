package es.uc3m.intour.dao;

import java.util.LinkedList;
import java.util.List;

import es.uc3m.intour.to.POI;

public class SPARQLPOIDAO implements POIDAO {

	/* (non-Javadoc)
	 * @see es.uc3m.intour.dao.POIDAO#search(java.lang.String)
	 */
	public static final String POI_QUERY="poi.query"; //See SPARQLQueriesLoader.properties
	public List<POI> search(String name) {
		//Create query
		String query = SPARQLQueriesLoader.getSPARQLQuery(POI_QUERY);
		//Execute query
		//Process results to create POIs
		POI poi = new POI();
		poi.setName("Name 1");
		List<POI> pois = new LinkedList<POI>();
		pois.add(poi);
		return pois;
	}

}
