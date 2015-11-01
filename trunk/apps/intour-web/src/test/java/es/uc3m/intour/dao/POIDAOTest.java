package es.uc3m.intour.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.POI;

public class POIDAOTest {

	@Test
	public void testSearch() {
		POIDAO dao = new SPARQLPOIDAO();
		Context context = new Context();
		context.setQuery("");
		List<POI> results = dao.search(context);
		System.out.println(results);
		assertEquals(10,results.size());
	}

}
