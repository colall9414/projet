package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class DiseaseState {
	
	Disease d;
	boolean isCured;
	boolean isEradicated;

	
	public DiseaseState(Disease d) {
		
		this.d = d;
		this.isCured = false;
		this.isEradicated = false;

	}
	
	public void cured(Disease d) {
		isCured = true;
	}

	public void eradicated(Disease d) {
		this.isEradicated=true;
	}
	public boolean getIsCured() {
		return this.isCured;
	}
	public boolean getEradicated() {
		return this.isEradicated;
	}
    public Disease getDisease() {
    	return this.d;
    }
  
	
	
	
	

}