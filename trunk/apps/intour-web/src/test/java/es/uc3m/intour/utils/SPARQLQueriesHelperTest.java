package es.uc3m.intour.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hp.hpl.jena.query.QuerySolution;

import es.uc3m.intour.dao.SPARQLPOIDAO;
import es.uc3m.intour.dao.SPARQLQueriesLoader;

public class SPARQLQueriesHelperTest {

	@Test
	public void testExecuteSimpleSparqlStringString() {
		String query = SPARQLQueriesLoader.getSPARQLQuery(SPARQLPOIDAO.POI_QUERY);
		String endpoint = "http://dbpedia.org/sparql";
		query = PrefixManager.NS+" "+query;//Important add the namespaces
		QuerySolution[] results = SPARQLQueriesHelper.executeSimpleSparql(endpoint, query);
		assertEquals(10, results.length);
	}

}
