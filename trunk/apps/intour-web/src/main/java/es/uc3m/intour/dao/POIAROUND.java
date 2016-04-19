package es.uc3m.intour.dao;

import java.util.List;

import es.uc3m.intour.to.POI;

public interface POIAROUND {
	
	public abstract List<POI> searchPOISAround(POI poi);

}
