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
public class ListEntity {
	
	
	private List<Entity> suggestion = new LinkedList<Entity>();
	
	public ListEntity(){
		super();	
	}
	public ListEntity(List<Entity> results) {
		this.suggestion = results;
	}

	public List<Entity> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<Entity> suggestion) {
		this.suggestion = suggestion;
	}
	
	

}
