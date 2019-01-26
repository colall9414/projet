package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.dauphine.ja.pandemiage.common.Disease;

public class CityStates {
	
	List<CityState> statesArray;
	
	public CityStates(CityLoader cl) {
		statesArray = new ArrayList<>();
		List<City> cities = cl.getCities();
		for(City city : cities){
			CityState cs = new CityState(city);
			statesArray.add(cs);
			//City city, Disease d, int yellowInfect, int blueInfect, int redInfect, int blackInfect
		}
	}
	public CityState getState(String cityName) {
		for(CityState cs : statesArray){
			if(cs.getCity().getName().equals(cityName)) {
				return cs;
			}
		}
		return null;
	}
	public int getInfectionLevel(String cityName,Disease d) {
		for(CityState cs : statesArray){
			if(cs.getCity().getName().equals(cityName)) {
				return cs.getAffectionLevel(d);
			}
		}
		return 0;
	}
	public void addInfectionLevel(String cityName,Disease d) {
		for(CityState cs : statesArray){
			if(cs.getCity().getName().equals(cityName)) {
			     cs.addInfectionLevel(d);
			}
		}
	}
	
	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		String filename = "./pandemic.graphml";
		CityLoader cl  = new CityLoader(filename);
		CityStates css = new CityStates(cl);
		css.addInfectionLevel("Chicago", Disease.BLACK);
		System.out.println(css.getInfectionLevel("Chicago", Disease.BLACK));
		System.out.println(css.getInfectionLevel("Chicago", Disease.BLUE));
		
	}
    
}
