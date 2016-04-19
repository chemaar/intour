package es.uc3m.intour.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.POI;

public class GooglePlacesDAIImplTest {

	@Test
	public void testSearch() {
		Context context = new Context();
		context.setQuery("Sabatini");
		POIDAO dao = new GooglePlacesDAIImpl();
		List<POI> pois = dao.search(context);
		assertNotNull(pois);
		assertEquals(5, pois.size());
	}

}
