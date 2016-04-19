package es.uc3m.intour.to;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "valuerouterequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "valuerouterequest", propOrder = {
    "POIorigen",
    "numEstrellas",
    "caracts"
})

public class ValueRouteRequest {

	@XmlElement public String POIorigen = "";
    @XmlElement public String numEstrellas = "";
    @XmlElement public List<String> caracts = new LinkedList<String>();
	
    public ValueRouteRequest(){
    	
    }
    
    public String getPOIorigen() {
		return POIorigen;
	}
	public void setPOIorigen(String pOIorigen) {
		POIorigen = pOIorigen;
	}
	public String getNumEstrellas() {
		return numEstrellas;
	}
	public void setNumEstrellas(String numEstrellas) {
		this.numEstrellas = numEstrellas;
	}
	public List<String> getCaracts() {
		return caracts;
	}
	public void setCaracts(List<String> caracts) {
		this.caracts = caracts;
	}
    
    
	
}
