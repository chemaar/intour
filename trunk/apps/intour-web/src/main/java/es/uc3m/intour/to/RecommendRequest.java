package es.uc3m.intour.to;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "routerequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "routerequest", propOrder = {
    "numStarts",
    "markers"
})
public class RecommendRequest {

	private String numStarts="";
	private List<POI> markers = new LinkedList<POI>();
	
	public RecommendRequest(){
    	
    }

	public String getNumStarts() {
		return numStarts;
	}

	public void setNumStarts(String numStarts) {
		this.numStarts = numStarts;
	}

	public List<POI> getMarkers() {
		return markers;
	}

	public void setMarkers(List<POI> markers) {
		this.markers = markers;
	}
	
}
