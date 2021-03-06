package es.uc3m.intour.to;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "routerequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "routerequest", propOrder = {
    "latOrigen",
    "lngOrigen",
    "others",
    "markers"
})
public class RouteRequest {
    
	private String latOrigen = "";
    private String lngOrigen = "";
    private List<POI> markers = new LinkedList<POI>();
    
    public RouteRequest(){
    	
    }
	public String getLatOrigen() {
		return latOrigen;
	}
	public void setLatOrigen(String latOrigen) {
		this.latOrigen = latOrigen;
	}
	public String getLngOrigen() {
		return lngOrigen;
	}
	public void setLngOrigen(String lngOrigen) {
		this.lngOrigen = lngOrigen;
	}
	public List<POI> getMarkers() {
		return markers;
	}
	public void setMarkers(List<POI> markers) {
		this.markers = markers;
	}
	
    
}
