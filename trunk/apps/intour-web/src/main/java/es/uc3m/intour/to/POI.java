package es.uc3m.intour.to;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import es.uc3m.intour.dao.GooglePlacesDAIImpl;

@XmlRootElement(name = "poi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "poi", propOrder = {
    "id",
    "uri",
    "name",
    "description",
    "type",
    "lat",
    "lon",
    "picture",
    "fuente",
    "address",
    "icono"
})
public class POI {

	private String id = "";
	private String uri= "";
	private String name= "";
	private String description= "";
	private String type = "";
	private String lat = "";
	private String lon = "";
	private String picture = GooglePlacesDAIImpl.DEFAULT_IMG;
	private String fuente= "";
	private String address= "";
	private String icono="";
	//private String icono="/img/marker_defecto.png";
	
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getFuente() {
		return fuente;
	}
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	
	
}
