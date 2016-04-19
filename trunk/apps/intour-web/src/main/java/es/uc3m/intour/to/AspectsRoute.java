package es.uc3m.intour.to;

import java.util.LinkedList;
import java.util.List;

public class AspectsRoute {
	
	private String POIorigen="";
	private List<EntityRoute> entities = new LinkedList<EntityRoute>();
	
	public AspectsRoute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPOIorigen() {
		return POIorigen;
	}

	public void setPOIorigen(String pOIorigen) {
		POIorigen = pOIorigen;
	}

	public List<EntityRoute> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityRoute> entities) {
		this.entities = entities;
	}
	
}
