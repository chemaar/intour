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
import es.uc3m.intour.to.Entity;
import es.uc3m.intour.to.ListEntity;


@Path("/entity")
public class SearchService {

	protected static Logger logger = Logger.getLogger(SearchService.class);
	
	SearchBusiness searcher = new SearchBusinessImpl();
	public SearchService() throws IOException{
		
	}

	@GET
	@Produces({"text/plain", "application/xml", "application/json"})
	public ListEntity searchEntity(@QueryParam("name") String name){	 
		try{
			logger.info("Searching by name:  "+name);
			Context context = new Context();
			context.setQuery(name);
			context.setName(name);
			List<Entity> results = this.searcher.searchEntity(context);
			
			/*Comprobamos si los datos se reciben bien*/
			if(results.isEmpty()){
				System.out.println("Entidad no encontrada");
			}else{
				System.out.println("Entidad encontrada");
			}
			ListEntity people = new ListEntity(results);
			return people;
		}catch(Exception e){
			 throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
	}
	
}
