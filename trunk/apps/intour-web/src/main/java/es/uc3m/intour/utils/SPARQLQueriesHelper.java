package es.uc3m.intour.utils;

import java.util.LinkedList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class SPARQLQueriesHelper {
	public static String DEFAULT_LANG="en";
	
	public static QuerySolution[] processResults(ResultSet resultsSet){
		LinkedList results = new LinkedList();
		for ( ; resultsSet.hasNext() ; )
		{
			QuerySolution soln = resultsSet.nextSolution() ;                    
			results.add(soln);                  
		}
		return (QuerySolution[]) results.toArray(new QuerySolution[results.size()]);
	}
	
	public static QuerySolution[] executeSimpleSparql(String endpoint, String queryStr) {
		LinkedList results = new LinkedList();
		Query query = QueryFactory.create(queryStr) ;
		QueryEngineHTTP httpQuery = new QueryEngineHTTP(endpoint,query);
		ResultSet resultsSet = httpQuery.execSelect();   
		return processResults(resultsSet);
	}
	
	public static QuerySolution[] executeSimpleSparql(String endpoint, Query query) {
		LinkedList results = new LinkedList();
		QueryEngineHTTP httpQuery = new QueryEngineHTTP(endpoint,query);
		ResultSet resultsSet = httpQuery.execSelect();   
		return processResults(resultsSet);
	}
	
	/**
	 * Against a local model
	 * @param model
	 * @param queryStr
	 * @return
	 */
	public static QuerySolution[] executeSimpleSparql(Model model, String queryStr) {
		LinkedList results = new LinkedList();        
		model.enterCriticalSection(Lock.READ) ;//Concurrency protect, it is  absolutely not neccesary but is recommended
		try{
			Query query = QueryFactory.create(queryStr) ;
			QueryExecution qexec = null;			
			try {
				qexec = QueryExecutionFactory.create(query, model) ;

				ResultSet resultsSet = qexec.execSelect();                
				for ( ; resultsSet.hasNext() ; )
				{
					QuerySolution soln = resultsSet.nextSolution() ;                    
					results.add(soln);                  
				}
			} finally { 
				qexec.close() ; 
			}
		}finally {
			model.leaveCriticalSection() ; 
		}        
		return (QuerySolution[]) results.toArray(new QuerySolution[results.size()]);
	}
	
	public static String[] fetchQuerySolutionToSimpleArray(QuerySolution []solutions,String field) {
		LinkedList results = new LinkedList();
		if(solutions != null){
			int size = solutions.length;
			for(int i = 0; i< size; i++){
				RDFNode node = solutions[i].get(field);
				String value= (node==null)?"": node.isLiteral() ?  solutions[i].getLiteral(field).getString(): node.toString();
				//Do not repeat values
				if(!results.contains(value)) results.add(value);
			}
		}
		return (String[]) results.toArray(new String[results.size()]);
	}
	
	public static boolean runQuestion(Model model,String queryString) {
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qe = QueryExecutionFactory.create(query, model);        
		return qe.execAsk();
	}

	public static Model executeDescribe(Model model, String queryStr) {
		Model mReturned = null;
		model.enterCriticalSection(Lock.READ) ;//Concurrency protect, it is  absolutely not neccesary but is recommended
		try{
			Query query = QueryFactory.create(queryStr) ;
			QueryExecution qexec = null;
			try {
				qexec = QueryExecutionFactory.create(query, model) ;

				mReturned = qexec.execDescribe();

			} finally {
				qexec.close() ;
			}
		}finally {
			model.leaveCriticalSection() ;
		}
		return mReturned;
	}


}
