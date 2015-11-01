package es.uc3m.intour.business;

import java.util.List;

import es.uc3m.intour.appserv.SearchAppServ;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.POI;

public class SearchBusinessImpl implements SearchBusiness{
	SearchAppServ appServ;
	public SearchBusinessImpl(){
		this.appServ = new SearchAppServ();
	}
	public List<POI> search(Context context){
		return this.appServ.search(context);
	}
}
