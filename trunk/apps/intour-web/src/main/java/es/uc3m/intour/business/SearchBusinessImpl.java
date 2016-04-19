package es.uc3m.intour.business;

import java.util.List;

import es.uc3m.intour.appserv.SearchAppServ;
import es.uc3m.intour.to.AspectsRoute;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.Entity;
import es.uc3m.intour.to.POI;

public class SearchBusinessImpl implements SearchBusiness{
	
	SearchAppServ appServ;
	public SearchBusinessImpl(){
		this.appServ = new SearchAppServ();
	}
	public List<POI> search(Context context){
		return this.appServ.search(context);
	}
	
	public List<Entity> searchEntity(Context context){
		return this.appServ.searchEntity(context);
	}
	public List<POI> generateRoute(ContextRoute contextoRutas){
		return this.appServ.generateRoute(contextoRutas);
	}
	
	public String valueRoute(AspectsRoute aspects,String numEstrellas){
		return this.appServ.valueRoute(aspects, numEstrellas);
	}
}
