package es.uc3m.intour.services;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import es.uc3m.intour.business.SearchBusiness;
import es.uc3m.intour.business.SearchBusinessImpl;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.ListPOI;
import es.uc3m.intour.to.POI;

@Path("/search")
public class SearchServicePlace {

	protected static Logger logger = Logger.getLogger(SearchServicePlace.class);
	
	SearchBusiness searcher = new SearchBusinessImpl();
	public SearchServicePlace() throws IOException{
		
	}

	@GET
	@Produces({"text/plain", "application/xml", "application/json"})
	public ListPOI search(@QueryParam("namePlace") String name,@QueryParam("input") String input){	 
		try{
			
			logger.info("Searching by namePlace:  "+name);
			System.out.println("El valor de input es: "+input);
			Context context = new Context();
			context.setQuery(name);
			context.setName(name);
			context.setInput(input);
			List<POI> results = this.searcher.search(context);
			ListPOI pois = new ListPOI(results);
			return pois;
		}catch(Exception e){
			 throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
	}
	
}
