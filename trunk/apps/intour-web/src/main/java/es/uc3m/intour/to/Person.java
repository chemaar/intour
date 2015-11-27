package es.uc3m.intour.to;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "person", propOrder = {
    "name",
    "birthPlace",
    "birthDate",
    "description",
    "nationality",
    "field",
    "museum"
})


public class Person {
	
	private String name="";
	private String birthPlace="";
	private String birthDate="";
	private String description="";
	private String nationality="";
	private String field="";
	private String museum="";
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMuseum() {
		return museum;
	}
	public void setMuseum(String museum) {
		this.museum = museum;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", birthPlace=" + birthPlace
				+ ", birthDate=" + birthDate + ", description=" + description
				+ ", nationality=" + nationality + ", field=" + field
				+ ", museum=" + museum + "]";
	}
	
	

}
