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
import es.uc3m.intour.to.POI;
import es.uc3m.intour.utils.PrefixManager;
import es.uc3m.intour.utils.SPARQLQueriesHelper;

public class SPARQLPOIDAO implements POIDAO {


	private String endpoint = "http://dbpedia.org/sparql"; //FIXME: Endpoint in the context?
	
	public static final String POI_QUERY="poi.query"; //See SPARQLQueriesLoader.properties
	
	public List<POI> search(Context context) {
		List<POI> pois = new LinkedList<POI>();
		//Create params
		Map<String,String> params = new HashMap<String,String>();
		params.put("lang", context.getLang());
		///params.put("place", context.getQuery());
		//Create query
		Query query = createQuery(POI_QUERY,params);
		//Execute query
		QuerySolution[] results = SPARQLQueriesHelper.executeSimpleSparql(endpoint, query);
		//Process results to create POIs
		String uriPOI;
		String label;
		String comment;
		String lat;
		String lon;
		for(int i = 0; i<results.length;i++){
			//FIXME: Processing: literal, literal with lang and resource
			label = extractStringValue(results[i],"label");
			uriPOI = (results[i].getResource("poi")!=null?results[i].getResource("poi").getURI():"");
			comment = extractStringValue(results[i],"description");
			lat = extractStringValue(results[i],"lat");
			lon = extractStringValue(results[i],"long");
			POI poi = new POI();
			poi.setUri(uriPOI);
			poi.setName(label);
			poi.setDescription(comment);
			poi.setLat(lat);
			poi.setLon(lon);
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
