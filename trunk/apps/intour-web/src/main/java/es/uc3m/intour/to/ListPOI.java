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
public class ListPOI {
	private List<POI> suggestion = new LinkedList<POI>();

	public ListPOI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListPOI(List<POI> results) {
		this.suggestion = results;
	}

	public List<POI> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<POI> suggestion) {
		this.suggestion = suggestion;
	}

	
}
