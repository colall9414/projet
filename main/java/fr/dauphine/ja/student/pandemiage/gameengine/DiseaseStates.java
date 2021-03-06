package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class DiseaseStates {
	
	List<DiseaseState> ldise;

	public DiseaseStates() {
		this.ldise = new ArrayList<>();
		ldise.add(new DiseaseState(Disease.BLACK));
		ldise.add(new DiseaseState(Disease.BLUE));
		ldise.add(new DiseaseState(Disease.RED));
		ldise.add(new DiseaseState(Disease.YELLOW));
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
	public void showDiseaseStates() {
		for(DiseaseState ds:ldise) {
			System.out.println(ds);
		}
	}
	
	

}