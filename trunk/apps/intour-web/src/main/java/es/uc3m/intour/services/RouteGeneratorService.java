package es.uc3m.intour.services;

import java.io.IOException;
import java.util.ArrayList;
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
	List<Double> latitudes = new LinkedList<Double>();
	List<Double> longitudes = new LinkedList<Double>();
	
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
	    
	    procesarDatos(input.getMarkers());
	    
	    ContextRoute contextoRutas = new ContextRoute();
	    contextoRutas.setLatOrigen(Double.parseDouble(input.getLatOrigen()));
	    contextoRutas.setLngOrigen(Double.parseDouble(input.getLngOrigen()));
	    contextoRutas.setLatitudes(latitudes);
	    contextoRutas.setLongitudes(longitudes);
	    
	    System.out.println("LATITUDES: ");
	    for(int i=0;i<latitudes.size();i++){
	    	System.out.println(latitudes.get(i));
	    }
	    System.out.println("LONGITUDES: ");
	    for(int i=0;i<longitudes.size();i++){
	    	System.out.println(longitudes.get(i));
	    }
	    
	    List<POI> puntos = this.searcher.generateRoute(contextoRutas);
	    
	    /*for(int i=0; i<puntos.size();i++){
	    	System.out.println("Punto "+i+": Lat->"+puntos.get(i).getLat()+" Lng->"+puntos.get(i).getLon());
	    }*/
	    
	    /*Route route = new Route();
	    route.setName("Generated "+System.currentTimeMillis());
	    route.setCamino(puntos);*/
	    
	    Route route = new Route();
	    route.setName("Generated "+System.currentTimeMillis());
	    //Calcula ruta
	    List<POI> camino = new LinkedList<POI>();
	    POI inicio = new POI();
	    inicio.setLat("40.3167");
	    inicio.setLon("-3.75");
	    inicio.setName("Lega");
	    inicio.setDescription("Ciudad");
		camino.add(inicio);
		route.setCamino(camino);
		return route;
	}
	
	public void procesarDatos(List<String> markers){
		
		double auxLat;
		double auxLng;
		
		for(int i=0;i<markers.size();i++){
			
			if(markers.get(i)!="{" && markers.get(i)!="}"){
				if(markers.get(i)=="lat"){
					auxLat=Double.parseDouble(markers.get(i+1));
					latitudes.add(auxLat);
				}else if(markers.get(i)=="lng"){
					auxLng=Double.parseDouble(markers.get(i+1));
					longitudes.add(auxLng);
				}
			}
		}	
		
	}

	
}

