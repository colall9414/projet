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
				// explode or not? //通过返回值判断是否爆发
				boolean breakOut=cs.addInfectionLevel(d, infectionRate);
				//如果爆发了
				if(breakOut==true) {
					//则把邻居也感染
					int nbOutBreak=0;//邻居爆发的次数
					List<String> neighbours = cl.neighbours(cityName);
					for(String cityNameNeighbours : neighbours) {
						//注意，这里的infectionRate是加一，而不是加infectionRate, 因为邻居是被感染波及的只会加1
						nbOutBreak+=this.epidemic(cityNameNeighbours, d, 1);
					}
					//结果数量上加一, 因为自己爆发了, 加上自己爆发的1
					return 1+nbOutBreak;
				}
				else {
					return 0;
				}
				//莫宝宝之前写的
				/*int nb = getInfectionLevel(cityName,d)+level;
					if(nb<=3) {
						cs.addInfectionLevel(d,level);
					}else {
						//never break out
						if(!breakOut) {
							
							breakOut=true;
							List<String> l = ge.neighbours(cityName);
							for(String s: l) {
								addInfectionLevel(s,d,1);
							}
							nbOutBreak++;
						}
					}
					*/
			}
		}
		//如果没找到改城市(理论上不可能发生的事情，则也返回0)
		return 0;
	}
	
	
	//蔓延卡病毒蔓延效果
	//cityLoader是为了获得邻居
	//city=>被感染的城市 disease=>被感染的病毒种类 infectionRate=>被感染程度(2或3或4)
	public int epidemic(String cityName, Disease d, int infectionRate) {
		//加上infectionRate次
		int nbOutbreaks = 0;
		nbOutbreaks+=addInfectionLevel(cityName,d, infectionRate);
		return nbOutbreaks;
	}
	//显示所有城市状况
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