package es.uc3m.intour.services;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import es.uc3m.intour.to.POI;
import es.uc3m.intour.to.Route;
import es.uc3m.intour.to.RouteRequest;

@Path("/route")
public class RouteGeneratorService {
	@POST 
	@Consumes({"application/json"})
	@Produces({"text/plain", "application/xml", "application/json"})
	public Route create(final RouteRequest input) {
	    System.out.println("param1 = " + input.param1);
	    System.out.println("param2 = " + input.param2);
	    System.out.println(input.getMarkers());
	    Route route = new Route();
	    route.setName("Generated "+System.currentTimeMillis());
	    //FIXME: Calcular ruta
	    List<POI> camino = new LinkedList<POI>();
	    POI inicio = new POI();
	    inicio.setLat("40.3167");
	    inicio.setLon("-3.75");
	    inicio.setName("Lega");
	    inicio.setDescription("Ciudad");
		camino.add(inicio );
		route.setCamino(camino);
		return route;
	}
	

	
}

