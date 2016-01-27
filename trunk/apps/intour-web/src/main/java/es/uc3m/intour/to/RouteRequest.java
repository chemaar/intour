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
    "param1",
    "param2",
    "others"
})
public class RouteRequest {
    @XmlElement public String param1 = "";
    @XmlElement public String param2 = "";
    @XmlElement public List<String> markers = new LinkedList<String>();
    public RouteRequest(){
    	
    }
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public List<String> getMarkers() {
		return markers;
	}
	public void setMarkers(List<String> markers) {
		this.markers = markers;
	}
	
    
}
