package es.uc3m.intour.appserv;

import java.util.LinkedList;
import java.util.List;

import es.uc3m.intour.dao.FoursquareDAIImpl;
import es.uc3m.intour.dao.GooglePlacesDAIImpl;
import es.uc3m.intour.dao.POIAROUND;
import es.uc3m.intour.dao.POIDAO;
import es.uc3m.intour.dao.POIROUTE;
import es.uc3m.intour.dao.RutasPOIROUTES;
import es.uc3m.intour.dao.SPARQLPOIDAO;
import es.uc3m.intour.to.AspectsRoute;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.Entity;
import es.uc3m.intour.to.POI;

public class SearchAppServ {

	
	List<POIDAO> poiDAOs;
	List<POIROUTE> poiRoutes;
	List<POIAROUND> poisAround;
	
	public SearchAppServ(){
		
		this.poiDAOs = new LinkedList<POIDAO>();
		
		/*DBPEDIA*/
		this.poiDAOs.add(new SPARQLPOIDAO());
		
		/*GooglePlaces*/
		//this.poiDAOs.add(new GooglePlacesDAIImpl());
		
		/*FourSquare*/
		this.poisAround = new LinkedList<POIAROUND>();
		this.poisAround.add(new FoursquareDAIImpl());
		
		/*RUTAS*/
		this.poiRoutes = new LinkedList<POIROUTE>();
		this.poiRoutes.add(new RutasPOIROUTES());
	}
	public List<POI> search(Context context){
		List<POI> pois = new LinkedList<POI>();
		//Para cada proveedor de lugares...
		for(POIDAO dao:this.poiDAOs){
			//Mezclar resultados
			pois.addAll(dao.search(context));
		}
		if(pois.isEmpty()){
			this.poiDAOs.add(new GooglePlacesDAIImpl());
			for(POIDAO dao:this.poiDAOs){
				pois.addAll(dao.search(context));
			}
		}
		/*else if(!pois.isEmpty() && !context.getInput().equals('7')){
			
			for(POIAROUND dao: this.poisAround){
				for(int i=0; i<pois.size();i++){
					pois.addAll(dao.searchPOISAround(pois.get(i)));
				}
			}
		}*/
		
		
		//Generar ruta...
		return pois;
	}
	
	public List<Entity> searchEntity(Context context){
		this.poiDAOs.add(new GooglePlacesDAIImpl());
		List<Entity> entities = new LinkedList<Entity>();
		//Para cada proveedor de lugares...
		for(POIDAO dao:this.poiDAOs){
			//Mezclar resultados
			entities.addAll(dao.searchEntity(context));
		}
		
		//Generar ruta...
		return entities;
	}
	
	public List<POI> generateRoute(ContextRoute contextoRutas){
		List<POI> pois = new LinkedList<POI>();
		//Para cada proveedor de lugares...
		for(POIROUTE dao:this.poiRoutes){
			//Mezclar resultados
			pois.addAll(dao.generateRoute(contextoRutas));
		}
		
		//Generar ruta...
		return pois;
	}
	public String valueRoute(AspectsRoute aspects,String numEstrellas) {
		// TODO Auto-generated method stub
		String result="";
		for(POIROUTE dao:this.poiRoutes){
			//Mezclar resultados
			result=dao.valueRoute(aspects,numEstrellas);
		}
		return result;
	}
}
