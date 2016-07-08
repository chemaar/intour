package es.uc3m.intour.appserv;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.intour.dao.FoursquareDAIImpl;
import es.uc3m.intour.dao.GooglePlacesDAIImpl;
import es.uc3m.intour.dao.POIAROUND;
import es.uc3m.intour.dao.POIDAO;
import es.uc3m.intour.dao.POIRECOMMEND;
import es.uc3m.intour.dao.POIROUTE;
import es.uc3m.intour.dao.RutasPOIROUTES;
import es.uc3m.intour.dao.SPARQLPOIDAO;
import es.uc3m.intour.to.AspectsRoute;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.ContextRoute;
import es.uc3m.intour.to.Entity;
import es.uc3m.intour.to.POI;

public class SearchAppServ {
	protected Logger logger = Logger.getLogger(SearchAppServ.class);
	
	List<POIDAO> poiDAOs;
	List<POIROUTE> poiRoutes;
	List<POIAROUND> poisAround;
	List<POIRECOMMEND> poisRecommended;
	
	public SearchAppServ(){
		
		this.poiDAOs = new LinkedList<POIDAO>();
		this.poisAround = new LinkedList<POIAROUND>();
		this.poiRoutes = new LinkedList<POIROUTE>();
		
		/*DBPEDIA*/
		this.poiDAOs.add(new SPARQLPOIDAO());
		
		/*FourSquare*/
		this.poisAround.add(new FoursquareDAIImpl());
		
		/*RUTAS*/
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
	
	public List<POI> searchPOISAround(List<POI> pois){
		List<POI> allPOIs = new LinkedList<POI>();
		for(POIAROUND dao:this.poisAround){
			allPOIs.addAll(dao.searchPOISAround(pois));
		}
		if(allPOIs.size()==pois.size()){
		    this.poisAround.add(new GooglePlacesDAIImpl());
			for(POIAROUND dao:this.poisAround){
				allPOIs.addAll(dao.searchPOISAround(pois));
			}
		}	
		return allPOIs;
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
	
	public String saveScores(List<POI> pois,String numEstrellas){
		String result="";
		for(POIRECOMMEND dao:this.poisRecommended){
			result=dao.saveScores(pois, numEstrellas);
		}
		return result;
	}
	
	public List<POI> recommendRoute(List<POI> pois){
		List<POI> poisRecommended = new LinkedList<POI>();
		for(POIRECOMMEND dao:this.poisRecommended){
			//Mezclar resultados
			poisRecommended.addAll(dao.recommendRoute(pois));
		}
		
		return poisRecommended;
	}
}
