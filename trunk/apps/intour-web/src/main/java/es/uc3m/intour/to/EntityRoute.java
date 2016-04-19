package es.uc3m.intour.to;

import java.util.LinkedList;
import java.util.List;

public class EntityRoute {

	private String name="";
	private List<String> caracts = new LinkedList<String>();
	
	public EntityRoute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCaracts() {
		return caracts;
	}

	public void setCaracts(List<String> characteristics) {
		this.caracts = characteristics;
	}
	
	
}
