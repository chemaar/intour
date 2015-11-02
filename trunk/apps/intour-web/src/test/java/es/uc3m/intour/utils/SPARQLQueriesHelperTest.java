package es.uc3m.intour.utils;

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.junit.Test;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.QuerySolution;

import es.uc3m.intour.dao.SPARQLPOIDAO;
import es.uc3m.intour.dao.SPARQLQueriesLoader;

public class SPARQLQueriesHelperTest {


	@Test
	public void testCustomizedSPARQL() {
		String queryTemplate = SPARQLQueriesLoader.getSPARQLQueryTemplate(SPARQLPOIDAO.POI_QUERY);
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
		pss.setLiteral("lang", "en");
     
		String endpoint = "http://dbpedia.org/sparql";
		String query =  pss.asQuery().toString();//Important add the namespaces
		QuerySolution[] results = SPARQLQueriesHelper.executeSimpleSparql(endpoint, query);
		assertEquals(10, results.length);
	}

}
