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
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.POI;
import es.uc3m.intour.to.Route;
import es.uc3m.intour.to.RouteRequest;



@Path("/route")
public class RouteGeneratorService {
	
	SearchBusiness searcher = new SearchBusinessImpl();
	
	public RouteGeneratorService() throws IOException{
		
	}
	
	@POST 
	@Consumes({"application/json"})
	@Produces({"text/plain", "application/xml", "application/json"})
	public Route create(final RouteRequest input) {
		
		/*Comprobamos que nos llegan los puntos de interes*/
		System.out.println("Latitud Origen: "+input.getLatOrigen());
		System.out.println("Longitud Origen: "+input.getLngOrigen());
	    System.out.println(input.getMarkers());
	    
	    ContextRoute contextoRutas = new ContextRoute();
	    contextoRutas.setLatOrigen(Double.parseDouble(input.getLatOrigen()));
	    contextoRutas.setLngOrigen(Double.parseDouble(input.getLngOrigen()));
	    contextoRutas.setPois(input.getMarkers());
	    
	    List<POI> camino = this.searcher.generateRoute(contextoRutas);
	    for(int i=0; i<camino.size();i++){
	    	System.out.println("Punto "+i+": Lat->"+camino.get(i).getLat()+" Lng->"+camino.get(i).getLon());
	    }
	    
	    List<POI> poisrecommended = this.searcher.recommendRoute(camino);
	    
	    for(int j=0;j< poisrecommended.size(); j++){
	    	System.out.println("Recomendado "+j+": Lat->"+poisrecommended.get(j).getLat()+" Lng->"+poisrecommended.get(j).getLon());
	    }
	    
	    Route route = new Route();
	    route.setName("Generated "+System.currentTimeMillis());
	    route.setCamino(camino);
		return route;
	}

	
}

