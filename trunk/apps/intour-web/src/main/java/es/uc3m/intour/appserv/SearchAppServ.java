package es.uc3m.intour.appserv;

import java.util.List;

import es.uc3m.intour.dao.POIDAO;
import es.uc3m.intour.dao.SPARQLPOIDAO;
import es.uc3m.intour.to.POI;

public class SearchAppServ {

	POIDAO poiDAO;
	public SearchAppServ(){
		this.poiDAO = new SPARQLPOIDAO();
	}
	public List<POI> search(String name){
		return this.poiDAO.search(name);
	}
}
