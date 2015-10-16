package es.uc3m.intour.services;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/search")
public class SearchService {

	public SearchService() throws IOException{
	}

	@GET
	@Path("person")
	@ProduceMime({"text/plain", "application/xml", "application/json"})
	public String search(@QueryParam("label") String name){	 
		try{
			return "Hola: "+name;
		}catch(Exception e){
			 throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
	}
	
}
