package es.uc3m.intour.dao;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Literal;

import es.uc3m.intour.to.Context;
import es.uc3m.intour.to.Entity;
import es.uc3m.intour.to.POI;
import es.uc3m.intour.utils.PrefixManager;
import es.uc3m.intour.utils.SPARQLQueriesHelper;

public class SPARQLPOIDAO implements POIDAO {


	private String endpoint = "http://dbpedia.org/sparql/"; //FIXME: Endpoint in the context?
	//private String endpoint = "http://dbpedia.org/snorql/"; //FIXME: Endpoint in the context?
	public static final String POI_QUERY="poi.query"; //See SPARQLQueriesLoader.properties
	public static final String PERSON_QUERY="person.query";
	public static String consulta;
	

	public List<Entity> searchEntity(Context context) {
		
		List<Entity> people = new LinkedList<Entity>();
		
		//Create params
		Map<String,String> params = new HashMap<String,String>();
		params.put("lang", context.getLang());
		params.put("name", context.getName());
		//Create query
		Query query = createQuery(PERSON_QUERY,params);
		//Execute query
		QuerySolution[] results = SPARQLQueriesHelper.executeSimpleSparql(endpoint, query);
		
		//Process results to create POIs
				String name;
				String birthPlace;
				String birthDate;
				String description;
				String nationality;
				String field;
				String museum;
				Entity person = new Entity();
				
				for(int i = 0; i<results.length;i++){
					//FIXME: Processing: literal, literal with lang and resource
					name = extractStringValue(results[i],"label");
					birthPlace = extractStringValue(results[i],"birthPlace");
					birthDate = extractStringValue(results[i],"birthDate");
					description = extractStringValue(results[i],"description");
					nationality = extractStringValue(results[i],"nationality");
					field = extractStringValue(results[i],"field");
					museum = extractStringValue(results[i],"museum");
					
					person.setName(name);
					person.setBirthPlace(birthPlace);
					person.setBirthDate(birthDate);
					person.setDescription(description);
					person.setNationality(nationality);
					person.setField(field);
					person.setMuseum(museum);
					/*System.out.println("Nombre: "+person.getName()+"birthPlace: "+person.getBirthPlace()+
							"Description: "+person.getDescription());*/
					people.add(person);
				}
		
		return people;
	
	}
	public List<POI> search(Context context) {
		
		List<POI> pois = new LinkedList<POI>();
		//Create params
		Map<String,String> params = new HashMap<String,String>();
		params.put("lang", context.getLang());
		params.put("name", context.getName());
		///params.put("place", context.getQuery());
		//Create query
		String input= context.getInput();
		
		if(input.equals("0")){
			consulta="poiPerson.query";
		}else if(input.equals("1")){
			consulta="poi.query";
		}else if(input.equals("2")){
			consulta="poiBirthDate.query";
		}else if(input.equals("3")){
			consulta="poiDescription.query";
		}else if(input.equals("4")){
			consulta="poiNationality.query";
		}else if(input.equals("5")){
			consulta="poiField.query";
		}else if(input.equals("6")){
			consulta="poiMuseum.query";
		}else if(input.equals("7")){
			consulta="poi.query";
		}
		
		Query query = createQuery(consulta,params);
		//Execute query
		QuerySolution[] results = SPARQLQueriesHelper.executeSimpleSparql(endpoint, query);
		//Process results to create POIs
		//String uriPOI;
		String label;
		//String comment;
		String lat;
		String lon;
		for(int i = 0; i<results.length;i++){
			//FIXME: Processing: literal, literal with lang and resource
			label = extractStringValue(results[0],"label");
			//uriPOI = (results[i].getResource("poi")!=null?results[i].getResource("poi").getURI():"");
			//comment = extractStringValue(results[i],"description");
			lat = extractStringValue(results[0],"lat");
			lon = extractStringValue(results[0],"long");
			POI poi = new POI();
			//poi.setUri(uriPOI);
			poi.setName(context.getName());
			//poi.setDescription(comment);
			poi.setLat(lat);
			poi.setLon(lon);
			poi.setFuente("http://wiki.dbpedia.org/");
			
			
			if(input.equals("1")){
				poi.setIcono("/img/placePOI.png");
			}else if(input.equals("2")){
				poi.setIcono("/img/calendarPOI.png");
			}else if(input.equals("3")){
				poi.setIcono("/img/descripctionPOI.png");
			}else if(input.equals("4")){
				poi.setIcono("/img/nationalityPOI.png");
			}else if(input.equals("5")){
				poi.setIcono("/img/fieldPOI.png");
			}else if(input.equals("6")){
				poi.setIcono("/img/museumPOI.png");
			}
			
			GooglePlacesDAIImpl googleplaces = new GooglePlacesDAIImpl();
			poi.setPicture(googleplaces.obtainPhoto(poi.getName()));
			
			pois.add(poi);
		}
		return pois;
	}

	private Query createQuery(String templateID, Map<String,String> varValues) {
		String queryTemplate = SPARQLQueriesLoader.getSPARQLQueryTemplate(templateID);
		ParameterizedSparqlString pss = 
				new ParameterizedSparqlString();
		ResourceBundle prefixes = PrefixManager.getResourceBundle();
		Enumeration<String> keys = prefixes.getKeys();
		while(keys.hasMoreElements()){
			String prefix = keys.nextElement();
			String uri = prefixes.getString(prefix);
			pss.setNsPrefix(prefix, uri);
		}
		pss.setCommandText(queryTemplate);
		//FIXME: Only literals
		for(String key:varValues.keySet()){
			pss.setLiteral(key, varValues.get(key));	
		}
		return pss.asQuery();
	}

	private static String extractStringValue(QuerySolution tuple, String field){
		Literal literal = tuple.getLiteral(field);
		if(literal != null){
			return literal.getString();
		}
		return "";
	}

}
