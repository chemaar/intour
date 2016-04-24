package es.uc3m.intour.dao;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.sprockets.google.Place;
import net.sf.sprockets.google.Place.Photo;
import net.sf.sprockets.google.Places;
import net.sf.sprockets.google.Places.Params;
import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.Entity;
import es.uc3m.intour.to.POI;

public class GooglePlacesDAIImpl implements POIDAO{

	//public static final String DEFAULT_IMG = "https://pixabay.com/static/uploads/photo/2014/05/22/16/24/beach-351232_960_720.jpg";
	public static final String DEFAULT_IMG = "http://t2.uccdn.com/images/4/6/2/img_los_mejores_lugares_para_visitar_en_asturias_21264_300_square.jpg";
	
	protected Logger logger = Logger.getLogger(GooglePlacesDAIImpl.class);
	
	public List<POI> search(Context context) {
		
		List<POI> pois = new LinkedList<POI>();
		try {

			List<Place> places = Places.textSearch(Params.create().query(context.getName())).getResult();
			POI poi = new POI();
			poi.setLat(String.valueOf(places.get(0).getLatitude()));
			poi.setLon(String.valueOf(places.get(0).getLongitude()));
			poi.setName(places.get(0).getName());
			poi.setPicture(selectPicture(places.get(0).getPhotos()));
			poi.setFuente("https://developers.google.com/places/web-service/");
			poi.setAddress(places.get(0).getFormattedAddress());
			pois.add(poi);
			
		} catch (IOException e) {
			
		}
		return pois;
	}

	private String selectPicture(List<Photo> photos) {
		String imgUri=DEFAULT_IMG;
		if(photos.size()>0){
			imgUri="https://maps.googleapis.com/maps/api/place/photo?photoreference="+
		photos.get(0).getReference()+"&sensor=false&maxheight=250&maxwidth=250&key="+
		CredentialsLoaderProperties.getCredential("google.api.key");
		}
		return imgUri;
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
