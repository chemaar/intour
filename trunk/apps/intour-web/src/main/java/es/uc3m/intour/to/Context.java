package es.uc3m.intour.to;

public class Context {

	private String lang = "en";
	private String name = "";
	private String input="";

	private String query = "";
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}


	
}
