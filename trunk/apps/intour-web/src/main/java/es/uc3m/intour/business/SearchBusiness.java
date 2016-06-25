package es.uc3m.intour.business;

import java.util.List;

import es.uc3m.intour.to.AspectsRoute;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.Entity;
import es.uc3m.intour.to.POI;

public interface SearchBusiness {
	List<POI> search(Context context);
	List<Entity> searchEntity(Context context);
	List<POI> searchPOISAround(List<POI> pois);
	List<POI> generateRoute(ContextRoute contextoRutas);
	String valueRoute(AspectsRoute aspects, String numEstrellas);
}
