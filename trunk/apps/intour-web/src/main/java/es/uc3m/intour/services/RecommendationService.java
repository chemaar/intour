package es.uc3m.intour.services;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import es.uc3m.intour.business.SearchBusiness;
import es.uc3m.intour.business.SearchBusinessImpl;
import es.uc3m.intour.to.POI;
import es.uc3m.intour.to.RecommendRequest;


@Path("/recommend")
public class RecommendationService {

	SearchBusiness searcher = new SearchBusinessImpl();
	
	public RecommendationService() throws IOException{
		
	}
	
	@POST 
	@Consumes({"application/json"})
	@Produces({"text/plain", "application/xml", "application/json"})
	public String create(final RecommendRequest input){
		
		System.out.println("numStarts: "+input.getNumStarts());
		System.out.println("Markers: "+input.getMarkers());
		List <POI> pois = new LinkedList<POI>();
		for(int i=0; i<input.getMarkers().size(); i++){
			POI poi = new POI();
			poi.setLat(input.getMarkers().get(i).getLat());
			poi.setLon(input.getMarkers().get(i).getLon());
			pois.add(poi);
		}
		
		String result= this.searcher.saveScores(pois,input.getNumStarts());
		return result;
	}
	
	
}
