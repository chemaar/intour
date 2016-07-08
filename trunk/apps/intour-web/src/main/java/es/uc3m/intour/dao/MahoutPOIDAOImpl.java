package es.uc3m.intour.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import es.uc3m.intour.to.Item;
import es.uc3m.intour.to.MahoutRecommender;
import es.uc3m.intour.to.POI;

public class MahoutPOIDAOImpl implements POIRECOMMEND {

	public static final Map<Item,Long> hashmap = new HashMap<Item,Long>();
	
	
	public List<POI> recommendRoute(List<POI> pois) {
		
		List<POI> poisRecommended = new LinkedList<POI>();
		for(int i=0; i<pois.size(); i++){
			try{
				Item item = new Item();
				item.setLatitud(Double.parseDouble(pois.get(i).getLat()));
				item.setLongitud(Double.parseDouble(pois.get(i).getLon()));
				
				if(hashmap.containsKey(item)){
					System.out.println(item.hashCode());
					MahoutRecommender mahout = new MahoutRecommender();
					List<RecommendedItem> recommendations = mahout.recommendItems(item.hashCode());
					if(!recommendations.isEmpty()){
						poisRecommended=obtainPOISrecommended(recommendations);
					}
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		return poisRecommended;
	}
	
	private List<POI> obtainPOISrecommended(List<RecommendedItem> recommendations){
		
		List<POI> pois = new LinkedList<POI>();
		for (RecommendedItem rm : recommendations) {
			POI poi=mapValuestoItems(rm.getItemID());
			pois.add(poi);
		}
		return pois;
	}
	private POI mapValuestoItems(long id){
	
		POI poi = new POI();
		String idaux= String.valueOf(id);
		Iterator iterador = hashmap.entrySet().iterator();
		Map.Entry recommendation;
		while (iterador.hasNext()) {
			recommendation = (Map.Entry) iterador.next();
	        if(idaux.equals(recommendation.getValue().toString())){
	        	Item item = (Item) recommendation.getKey();
	        	poi.setLat(String.valueOf(item.getLatitud()));
	        	poi.setLon(String.valueOf(item.getLongitud()));
	        }
	    } 
		return poi;
	}
	
	public String saveScores(List<POI> pois,String numEstrellas){
		
		long id1=0;
		long id2=0;
		String result="";		
		int firstTime=0;
		
		for(int i=0;i<pois.size();i++){
			for(int j=0;j<pois.size();j++){
				if(i!=j && j>i){
					if(firstTime==0){
						saveHashcodes(pois.get(i));
						firstTime=1;
					}
					id1=generateID(pois.get(i));
					id2=generateID(pois.get(j));
					writeFile(id1,id2,numEstrellas);
				}
			}
			firstTime=0;
		}	
		result="OK";
		
		return result;
	}
	
	private void saveHashcodes(POI poi){
		Item item = new Item();
		item.setLatitud(Double.parseDouble(poi.getLat()));
		item.setLongitud(Double.parseDouble(poi.getLon()));
		long id=item.hashCode();
		hashmap.put(item, id);
	}
	
	private long generateID(POI poi){
		
		Item item = new Item();
		item.setLatitud(Double.parseDouble(poi.getLat()));
		item.setLongitud(Double.parseDouble(poi.getLon()));
		long id=item.hashCode();
		return id;
	}
	
	private void writeFile(long id1, long id2, String numStarts){
		
		String ID1=String.valueOf(id1);
		String ID2=String.valueOf(id2);
		String texto=ID1+","+ID2+","+ numStarts+".0";
		try
		{
			//Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
			File file=new File("src/main/resources/recommendations.txt");
			
			//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
			FileWriter escribir=new FileWriter(file,true);

			//Escribimos en el archivo con el metodo write 
			escribir.write(texto);
			escribir.write("\n");
			//Cerramos la conexion
			escribir.close();
			
		}catch(Exception e){
			System.out.println("Error al escribir");
		}
		
	}



}
