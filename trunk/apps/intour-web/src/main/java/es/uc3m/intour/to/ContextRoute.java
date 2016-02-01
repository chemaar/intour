package es.uc3m.intour.to;

import java.util.List;

public class ContextRoute {

	private double latOrigen;
	private double lngOrigen;
	private List<Double> latitudes;
	private List<Double> longitudes;
	
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
	public List<Double> getLatitudes() {
		return latitudes;
	}
	public void setLatitudes(List<Double> latitudes) {
		this.latitudes = latitudes;
	}
	public List<Double> getLongitudes() {
		return longitudes;
	}
	public void setLongitudes(List<Double> longitudes) {
		this.longitudes = longitudes;
	}
	
	
	
}
