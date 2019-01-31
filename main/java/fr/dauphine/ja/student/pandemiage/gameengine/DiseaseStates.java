package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class DiseaseStates {
	
	List<DiseaseState> ldise;

	public DiseaseStates() {

		this.ldise = new ArrayList<>();
	}
	public boolean isCured(Disease d) {
		
		for(DiseaseState ds: ldise) {
			if(ds.getDisease().equals(d)) {
				return ds.getIsCured();
			}
		}
		return false;
		
	}
	/*public boolean isEradicated(Disease d) {
		for(DiseaseState ds: ldise) {
			if(ds.getDisease().equals(d)) {
				return ds.getEradicated();
			}
		}
		return false;
	}*/
	public void discoverCure(Disease d) {
		for(DiseaseState ds: ldise) {
			if(ds.getDisease().equals(d)) {
				ds.cured();
			}
		}
	}
	public List<DiseaseState> getLdise() {
		return ldise;
	}
	public void setLdise(List<DiseaseState> ldise) {
		this.ldise = ldise;
	}
	

}