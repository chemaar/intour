package es.uc3m.intour.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.uc3m.intour.to.AspectsRoute;
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.Item;
import es.uc3m.intour.to.POI;

public class RutasPOIROUTES implements POIROUTE {
	
	Map<AspectsRoute,String> hashmap = new HashMap<AspectsRoute,String>();

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
	
	public String valueRoute(AspectsRoute aspects, String numEstrellas) {
		
		String result="";
		//Comprobamos que se ha introducido en memoria local
		Object value = hashmap.get(aspects);
		if (value == null) {
		    hashmap.put(aspects,numEstrellas);
		    result="OK";
		}else{
			System.out.println("RUTA-VALORACION YA INTRODUCIDA");
			result="ERR";
		}
		return result;
	}
	
	public String recommendRoute(List<POI> pois,String numEstrellas){
		
		int aux=1;
		int id1=0;
		int id2=0;
		String result="";		
		
		int i=0;
		while(i<pois.size()){
			
			if(aux<pois.size()-1){
				id1=generateID(pois.get(i));
				id2=generateID(pois.get(aux));
				writeFile(id1,id2,numEstrellas);
				aux++;
			}
			if(aux==pois.size()-1){
				id1=generateID(pois.get(i));
				id2=generateID(pois.get(aux));
				writeFile(id1,id2,numEstrellas);
				i++;
				aux=i+1;
			}
		}
		result="OK";
		
		return result;
	}
	
	private int generateID(POI poi){
		
		Item item = new Item();
		item.setLatitud(Double.parseDouble(poi.getLat()));
		item.setLongitud(Double.parseDouble(poi.getLon()));
		int id=item.hashCode();
		return id;
	}
	
	private void writeFile(int id1, int id2, String numStarts){
		
		String ID1=String.valueOf(id1);
		String ID2=String.valueOf(id2);
		String texto=ID1+" | "+ID2+" | "+ numStarts;
		try
		{
			//Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
			File file=new File("C:/Users/Marta/Desktop/recommendations.txt");

			//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
			FileWriter escribir=new FileWriter(file,true);

			//Escribimos en el archivo con el metodo write 
			escribir.write(texto);
			escribir.write("\n");
			//Cerramos la conexion
			escribir.close();
			
		}catch(Exception e){
			System.out.println("Error al escribir");
		}
		
	}
	
}
