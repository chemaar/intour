package es.uc3m.intour.to;

import java.util.List;

public class ContextRoute {

	private double latOrigen;
	private double lngOrigen;
	private List<POI> pois;
	
	
	public ContextRoute(){
		
	}
	
	public double getLatOrigen() {
		return latOrigen;
	}
	public void setLatOrigen(double latOrigen) {
		this.latOrigen = latOrigen;
	}
	public double getLngOrigen() {
		return lngOrigen;
	}
	public void setLngOrigen(double lngOrigen) {
		this.lngOrigen = lngOrigen;
	}

	public List<POI> getPois() {
		return pois;
	}

	public void setPois(List<POI> pois) {
		this.pois = pois;
	}

	
	
}
