package es.uc3m.intour.appserv;

import java.util.List;

import es.uc3m.intour.dao.POIDAO;
import es.uc3m.intour.dao.SPARQLPOIDAO;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.POI;
import es.uc3m.intour.to.Person;

public class SearchAppServ {

	POIDAO poiDAO;
	public SearchAppServ(){
		this.poiDAO = new SPARQLPOIDAO();
	}
	public List<POI> search(Context context){
		//Mezclar resultados
		//Generar ruta...
		return this.poiDAO.search(context);
	}
	
	public List<Person> searchPerson(Context context){
		//Mezclar resultados
		//Generar ruta...
		return this.poiDAO.searchPerson(context);
	}
}
