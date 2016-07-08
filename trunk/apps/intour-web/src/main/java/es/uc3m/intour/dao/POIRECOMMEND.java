package es.uc3m.intour.dao;

import java.util.List;

import es.uc3m.intour.to.POI;

public interface POIRECOMMEND {

	public abstract List<POI> recommendRoute(List<POI> pois);
	public abstract String saveScores(List<POI> pois,String numEstrellas);
}
