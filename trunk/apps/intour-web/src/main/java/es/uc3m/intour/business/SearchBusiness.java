package es.uc3m.intour.business;

import java.util.List;

import es.uc3m.intour.to.POI;

public interface SearchBusiness {
	List<POI> search(String name);
}
