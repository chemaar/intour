package es.uc3m.intour.dao;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import net.sf.sprockets.google.Place;
import net.sf.sprockets.google.Places;
import net.sf.sprockets.google.Places.Params;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.Entity;
import es.uc3m.intour.to.POI;

public class GooglePlacesDAIImpl implements POIDAO{

	protected Logger logger = Logger.getLogger(GooglePlacesDAIImpl.class);
	
	public List<POI> search(Context context) {
		
		List<POI> pois = new LinkedList<POI>();
		try {

			List<Place> places = Places.textSearch(Params.create().query(context.getName())).getResult();
			POI poi = new POI();
			poi.setLat(String.valueOf(places.get(0).getLatitude()));
			poi.setLon(String.valueOf(places.get(0).getLongitude()));
			poi.setName(places.get(0).getName());
			//poi.setPhotos(String.valueOf(places.get(i).getPhotos()));			
			pois.add(poi);
			
		} catch (IOException e) {
			
		}
		return pois;
	}

	public List<Entity> searchEntity(Context context) {
		
		List<Entity> entities = new LinkedList<Entity>();
		try{
			List<Place> places = Places.textSearch(Params.create().query(context.getName())).getResult();
			for(int i=0; i<5; i++){
				if(i>places.size()) break;
				else{
					Entity entity = new Entity();
					entity.setName(places.get(i).getName());
					entities.add(entity);
				}		
			}
			
			
		}catch(Exception e){
			
		}
		
		return entities;
	}

}
