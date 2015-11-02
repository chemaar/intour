package es.uc3m.intour.dao;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SPARQLQueriesLoader {
	protected static final String SPARQL_LOCAL_DIR = "sparql/queries";
	private static HashMap<String, String> sparqlQueries; 


	static{
		ResourceBundle sparqlBundle = ResourceBundle.getBundle(
				SPARQLQueriesLoader.class.getName().toString());   
		sparqlQueries = new HashMap<String,String>();
		Enumeration<String> keys = sparqlBundle.getKeys();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			String queryFile = sparqlBundle.getString(key);
			String content = readFile(SPARQL_LOCAL_DIR+File.separator+queryFile, StandardCharsets.UTF_8);
			sparqlQueries.put(key, content);	
		}

	}


	public static String  getSPARQLQueryTemplate(String key){
		return  sparqlQueries.get(key);
	}

	public static HashMap<String, String>  getSPARQLQueryTemplates(){
		return  sparqlQueries;
	}

	static String readFile(String path, Charset encoding) {
		try{
			byte[] encoded = Files.readAllBytes(
	                		 Paths.get(Thread.currentThread().getContextClassLoader()
	                        .getResource(path)
	                        .toURI()));
			return new String(encoded, encoding);
		}catch(Exception e){
			
		}
		return "";
	}

}
