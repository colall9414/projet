package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class DiseaseState {
	
	Disease d;
	boolean isCured;

	
	public DiseaseState(Disease d) {
		
		this.d = d;
		this.isCured = false;

	}
	
	public void cured() {
		isCured = true;
	}
	public boolean getIsCured() {
		return this.isCured;
	}
    public Disease getDisease() {
    	return this.d;
    }

	@Override
	public String toString() {
		return "DiseaseState [d=" + d + ", isCured=" + isCured + "]";
	}
    
  
	
	
	
	

}