package es.uc3m.intour.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.intour.to.POI;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareDAIImpl implements POIAROUND {

	protected Logger logger = Logger.getLogger(FoursquareDAIImpl.class);
	
	public static String creden1=CredentialsLoaderProperties.getCredential("credenciales1");
	public static String creden2=CredentialsLoaderProperties.getCredential("credenciales2");

	public List<POI> searchPOISAround(POI poi) {

		List<POI> pois = new LinkedList<POI>();
		try{

			String ll= poi.getLat()+","+poi.getLon();
			
			//String ll="51.568 ,0.065532";
			// First we need a initialize FoursquareApi. 
			FoursquareApi foursquareApi = new FoursquareApi(creden1, creden2, "http://github.com/chemaar/intour");

			foursquareApi.setVersion("20131016");


			// After client has been initialized we can make queries.
			Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll,null, null, null,null,5, null,null,null,null,null,10000,ll);
		
			if (result.getMeta().getCode() == 200) {
				// if query was ok we can finally we do something with the data
				for (CompactVenue venue : result.getResult().getVenues()) {
					// TODO: Do something we the data
					POI newPoi = new POI();
					newPoi.setName(venue.getName());
					newPoi.setLat(String.valueOf(venue.getLocation().getLat()));
					newPoi.setLon(String.valueOf(venue.getLocation().getLng()));
					pois.add(newPoi);
				}
			} else {
				// TODO: Proper error handling
				logger.error("Error occured: "+
				"  code: " + result.getMeta().getCode()+
				"  type: " + result.getMeta().getErrorType()+
				"  detail: " + result.getMeta().getErrorDetail()); 
			}

		}catch(Exception e){
			logger.error(e);
		}


		return pois;
	}


}
