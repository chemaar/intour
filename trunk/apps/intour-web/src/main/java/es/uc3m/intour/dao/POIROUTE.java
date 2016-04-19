package es.uc3m.intour.dao;

import java.util.List;

import es.uc3m.intour.to.AspectsRoute;
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.POI;

public interface POIROUTE {
	
	public abstract List<POI> generateRoute(ContextRoute contextoRutas);
	public abstract String valueRoute(AspectsRoute aspects, String numEstrellas);

}
