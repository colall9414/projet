package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class DiseaseState {
	
	Disease d;
	boolean isCuried;
	
	public DiseaseState(Disease d) {
		
		this.d = d;
		this.isCuried = false;
	}
	
	public void curied(Disease d) {
		
		
		isCuried = true;
		/*
		int count=0; //The number of city cards with corresponding colors
		int nbcard=5;
		List<Card> lcards = cards.getCityCards();
		for(Card c:lcards) {
			if(c.getDisease().equals(d)) {
				count++;
			}
			if(count==nbcard) {
				isCuried = true;
				
			}
		}
		if(isCuried) {
			for(Card c:lcards) {
				if(c.getDisease().equals(d)&&nbcard>0) {
			         cards.remove(c);
			         nbcard--;
				}
			}
		}
		*/
	}

	public boolean getIsCuried() {
		return isCuried;
	}
    public Disease getDisease() {
    	return d;
    }
	
	
	
	

}
