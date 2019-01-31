package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.dauphine.ja.pandemiage.common.Disease;

public class CityStates {
	
	CityLoader cl;
	List<CityState> statesArray;
	
	public CityStates(CityLoader cl) {
		this.cl=cl;
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
	public int addInfectionLevel(String cityName, Disease d, int infectionRate) {
		for(CityState cs : statesArray){
			if(cs.getCity().getName().equals(cityName)) {
				// explode or not? 
				boolean breakOut=cs.addInfectionLevel(d, infectionRate);
				//if explode
				if(breakOut==true) {
					//Then infected the neighbors
					int nbOutBreak=0;//Number of neighbor outbreaks
					List<String> neighbours = cl.neighbours(cityName);
					for(String cityNameNeighbours : neighbours) {
						
						nbOutBreak+=this.epidemic(cityNameNeighbours, d, 1);
					}
					//Add one to the number of results, because you also broke out
					return 1+nbOutBreak;
				}
				else {
					return 0;
				}
				
			}
		}
		//If you don't find a change to the city (then things that can't happen theoretically, it also returns 0)
		return 0;
	}
	
	
	//if the player draw a epdemic card
	//city=>Infected citydisease=>Infected virus species infectionRate=>Degree of infection(2or3or4)
	public int epidemic(String cityName, Disease d, int infectionRate) {

		int nbOutbreaks = 0;
		nbOutbreaks+=addInfectionLevel(cityName,d, infectionRate);
		return nbOutbreaks;
	}
	//Treating the city's virus, numbre of virus d becoming 0
	public void cubeToZero(Disease d, String cityName) {
		for(CityState cs:statesArray) {
			if(cs.getCity().getName().equals(cityName)) {
				cs.cubeToZero(d);
			}
		}
	}
	//Subtract a virus of the corresponding color
	public void minusUnCube(Disease d, String cityName) {
		for(CityState cs:statesArray) {
			if(cs.getCity().getName().equals(cityName)) {
				cs.minusUnCube(d);
			}
		}
	}
	//Show all city conditions
	public void showCityStates() {
		for(CityState cs: statesArray) {
			System.out.println(cs);
		}
	}
	
	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		String filename = "./pandemic.graphml";
		String aijar = "./target/pandemiage-1.0-SNAPSHOT-ai.jar";
		CityLoader cl  = new CityLoader(filename);
		CityStates css = new CityStates(cl);
		css.addInfectionLevel("Atlanta", Disease.BLACK,3);
		css.addInfectionLevel("Chicago", Disease.BLACK,3);
		css.addInfectionLevel("Washington", Disease.BLACK,3);
		css.addInfectionLevel("Miami", Disease.BLACK,3);
		css.addInfectionLevel("Atlanta", Disease.BLACK,1);
		css.showCityStates();
		//System.out.println(css.getInfectionLevel("Chicago", Disease.BLACK));
		//System.out.println(css.getInfectionLevel("Chicago", Disease.BLUE));
		
	}
    
}