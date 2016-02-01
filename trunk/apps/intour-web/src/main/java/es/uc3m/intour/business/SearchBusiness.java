package es.uc3m.intour.business;

import java.util.List;

import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.POI;
import es.uc3m.intour.to.Person;

public interface SearchBusiness {
	List<POI> search(Context context);
	List<Person> searchPerson(Context context);
	List<POI> generateRoute(ContextRoute contextoRutas);
}
