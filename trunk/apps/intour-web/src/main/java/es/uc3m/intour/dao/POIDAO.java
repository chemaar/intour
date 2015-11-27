package es.uc3m.intour.dao;

import java.util.List;

import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.POI;
import es.uc3m.intour.to.Person;

public interface POIDAO {

	public abstract List<POI> search(Context context);
	public abstract List<Person> searchPerson(Context context);

}