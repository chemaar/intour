package es.uc3m.intour.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.uc3m.intour.to.AspectsRoute;
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.POI;

public class RutasPOIROUTES implements POIROUTE {
	
	public static final Map<AspectsRoute,String> hashmap = new HashMap<AspectsRoute,String>();

	public List<POI> generateRoute(ContextRoute contextoRutas){
		
		double latOrigen=contextoRutas.getLatOrigen();
		double lngOrigen=contextoRutas.getLngOrigen();
		List<POI> poisReq= contextoRutas.getPois();
		List<POI> ruta = new LinkedList<POI>();
	    POI inicio = new POI();
	    inicio.setName("Punto de Origen");
	    inicio.setLat(String.valueOf(latOrigen));
	    inicio.setLon(String.valueOf(lngOrigen));
	    inicio.setPicture("http://www.saracosta.com/callejon/wp-content/uploads/2011/06/aqui.jpg");
	    inicio.setIcono("/img/origen.png");
	    ruta.add(inicio);
	    int posicion=0;
	    int numPoints=poisReq.size()+1;
	    
	    while(ruta.size()!=numPoints){
	    	POI auxPoi = new POI();
	    	auxPoi=calcularPuntoCercano(ruta,posicion,contextoRutas);
	    	ruta.add(auxPoi);
	    	posicion++;
	    }
		return ruta; 
	}
	
	private double calcularDistancia(double latOrig, double lngOrig, double latDest, double lngDest){
		
		double distancia=0;
		double difLat;
		double difLng;
		double squareLat;
		double squareLng;
		double sumsquare;
		double aux;
		
		if(latOrig>=0){
			difLat=latDest-latOrig;
		}else{
			aux=Math.abs(latOrig);
			difLat=latDest+aux;
		}
		if(lngOrig>=0){
			difLng=lngDest-lngDest;
		}else{
			aux=Math.abs(lngOrig);
			difLng=lngDest+aux;
		}
		
		squareLat=Math.pow(difLat, 2);
		squareLng=Math.pow(difLng, 2);
		sumsquare=squareLat+squareLng;
		distancia=Math.sqrt(sumsquare);
		return distancia;
	}
	
	private POI calcularPuntoCercano(List<POI> ruta,int posicion,ContextRoute contextoRutas){
		
		List<POI> poisReq= contextoRutas.getPois();
		double auxLat=Double.parseDouble(ruta.get(posicion).getLat());
		double auxLng=Double.parseDouble(ruta.get(posicion).getLon());
		POI puntoCercano = new POI();
		double distanciaMin=Double.MAX_VALUE;
	    double distancia;
	    
		
		for(int i=0; i<poisReq.size();i++){
			
			distancia=calcularDistancia(auxLat,auxLng,Double.parseDouble(poisReq.get(i).getLat()),Double.parseDouble(poisReq.get(i).getLon()));
			System.out.println("DISTANCIA "+i+1+": "+distancia);
			boolean existe=comprobarSiExiste(ruta,poisReq.get(i).getLat(),poisReq.get(i).getLon());
			if(distancia<=distanciaMin && distancia!=0 && existe==false){
	    		distanciaMin=distancia;
	    		puntoCercano.setLat(poisReq.get(i).getLat());
	    		puntoCercano.setLon(poisReq.get(i).getLon());
	    		puntoCercano.setName(poisReq.get(i).getName());
	    		puntoCercano.setPicture(poisReq.get(i).getPicture());
	    		puntoCercano.setFuente(poisReq.get(i).getFuente());
	    		puntoCercano.setAddress(poisReq.get(i).getAddress());
	    		puntoCercano.setIcono(poisReq.get(i).getIcono());
	    	}	   	
		}
		
		return puntoCercano;
	}
	
	private boolean comprobarSiExiste(List<POI> ruta,String lat, String lng){
		
		for(int i=0; i<ruta.size();i++){
			if(ruta.get(i).getLat().equals(lat) && ruta.get(i).getLon().equals(lng)){
				return true;
			}
		}
		return false;
	}
	
	public String valueRoute(AspectsRoute aspects, String numEstrellas){
		
		String result="";
		//Comprobamos que se ha introducido en memoria local
		boolean contiene = hashmap.containsKey(aspects);
		if (contiene == false) {
		    hashmap.put(aspects,numEstrellas);
		    result="OK";
		}else{
			System.out.println("RUTA-VALORACION YA INTRODUCIDA");
			result="ERR";
		}
		return result;
	}

	
}
