package es.uc3m.intour.to;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "suggestions")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "suggestions", propOrder = {
    "suggestion"
})
public class ListPerson {
	
	
	private List<Person> suggestion = new LinkedList<Person>();
	
	public ListPerson(){
		super();	
	}
	public ListPerson(List<Person> results) {
		this.suggestion = results;
	}

	public List<Person> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<Person> suggestion) {
		this.suggestion = suggestion;
	}
	
	

}
