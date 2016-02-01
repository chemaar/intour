package es.uc3m.intour.to;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement(name = "route")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "route", propOrder = {
    "camino",
    "name"
})
public class Route {
	private String name="";
	private List<POI> camino = new LinkedList<POI>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<POI> getCamino() {
		return camino;
	}
	public void setCamino(List<POI> camino) {
		this.camino = camino;
	}
	public Route() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
