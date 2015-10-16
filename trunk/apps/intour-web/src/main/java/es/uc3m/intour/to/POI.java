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
    "description"
})
public class POI {

	private String id = "";
	private String uri= "";
	private String name= "";
	private String description= "";
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
	
	
}
