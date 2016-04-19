package es.uc3m.intour.services;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import es.uc3m.intour.business.SearchBusiness;
import es.uc3m.intour.business.SearchBusinessImpl;
import es.uc3m.intour.to.AspectsRoute;
import es.uc3m.intour.to.EntityRoute;
import es.uc3m.intour.to.ValueRouteRequest;

@Path("/valorar")
public class ValueRouteService {

	SearchBusiness searcher = new SearchBusinessImpl(); 
	List <EntityRoute> entities = new LinkedList<EntityRoute>();
	
	public ValueRouteService() throws IOException{
		
	}
	
	@POST 
	@Consumes({"application/json"})
	@Produces({"text/plain", "application/xml", "application/json"})
	public String create(final ValueRouteRequest input) {
		
		/*Comprobamos que nos llegan los puntos de interes*/
		System.out.println("Punto Origen: "+input.getPOIorigen());
		System.out.println("Numero Estrellas: "+input.getNumEstrellas());
		
		List<String> preprocess = preprocesarCaracts(input.getCaracts());
	    procesarCaracts(preprocess);
	    
	  //Imprimimos para comprobar que se procesan bien los datos
  		for(int j=0; j<entities.size(); j++){
  			System.out.println("NameEntity "+j+" :"+entities.get(j).getName());
  			System.out.println("Caracts "+j+" :"+entities.get(j).getCaracts());
  		}
	    
	    AspectsRoute aspects = new AspectsRoute();
	    aspects.setPOIorigen(input.getPOIorigen());
	    aspects.setEntities(entities);
	    String result = this.searcher.valueRoute(aspects, input.getNumEstrellas());
	    System.out.println("RESULTADO VALORACION: "+result);
	    return result;
	}
	
	public List<String> preprocesarCaracts(List<String> caracts){
		caracts.remove(0);
		caracts.add("#");
		
		List<String> caractAux = new LinkedList<String>();
		int cont=0;
		int aux1=0;
		int aux2=1;
		while(aux1>=0 && cont<=caracts.size()-2){
			
			if(!caracts.get(aux1).equals("#") || !caracts.get(aux2).equals("#")){
				caractAux.add(caracts.get(cont));
			}
			if(cont!=0) aux1++;
			cont++;
			aux2++;
		}
		if(!caractAux.get(caractAux.size()-1).equals("#")){
			caractAux.add("#");
		}
		
		for(int j=0; j<caractAux.size();j++){
			if(caractAux.get(j).equals("#") && j>=0){
				if(caractAux.get(j-1).equals("#")){
					caractAux.remove(j);
				}
			}
		}
		
	    System.out.println(caractAux);
	    return caractAux;
		
	}
	public void procesarCaracts(List<String> caracts){
		
		String name="";
		List <String> characts = new LinkedList<String>();
		
		for(int i=0; i<caracts.size(); i++){
			
			if(!caracts.get(i).equals("#")){
				if(i==0 || caracts.get(i-1).equals("#")){
					name=caracts.get(i);
				} 
				else{
					characts.add(caracts.get(i));
				}
			}else{
				EntityRoute entity = new EntityRoute();
				List <String> auxcharacts = new LinkedList<String>();
				for(int j=0; j<characts.size();j++){
					auxcharacts.add(characts.get(j));
				}
				entity.setName(name);
				entity.setCaracts(auxcharacts);
				entities.add(entity);
				characts.clear();
			}
			
		}
		
		for(int i=0;i<entities.size();i++){
	    	if(entities.get(i).getCaracts().isEmpty()){
	    		entities.remove(i);
	    	}
	    }
		
		
		
	}
	
	
}
