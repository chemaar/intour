package es.uc3m.intour.services;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import es.uc3m.intour.business.SearchBusiness;
import es.uc3m.intour.business.SearchBusinessImpl;
import es.uc3m.intour.to.ListPOI;
import es.uc3m.intour.to.POI;

@Path("/search")
public class SearchService {

	SearchBusiness searcher = new SearchBusinessImpl();
	public SearchService() throws IOException{
	}

	//@Path("person")
	@GET
	@ProduceMime({"text/plain", "application/xml", "application/json"})
	public ListPOI search(@QueryParam("name") String name){	 
		try{
			List<POI> results = this.searcher.search(name);
			ListPOI pois = new ListPOI(results);
			return pois;
		}catch(Exception e){
			 throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
	}
	
}
