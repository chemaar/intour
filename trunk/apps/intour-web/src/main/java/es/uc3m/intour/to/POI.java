package es.uc3m.intour.to;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "poi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "poi", propOrder = {
    "id",
    "uri",
    "name",
    "description",
    "type",
    "lat",
    "lon"
})
public class POI {

	private String id = "";
	private String uri= "";
	private String name= "";
	private String description= "";
	private String type = "";
	private String lat = "";
	private String lon = "";
	
	public POI(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "POI [id=" + id + ", uri=" + uri + ", name=" + name
				+ ", description=" + description + ", type=" + type + ", lat="
				+ lat + ", lon=" + lon + "]";
	}

	
	
}
