package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.*;

import fr.dauphine.ja.pandemiage.common.Disease;

import javax.xml.parsers.*;

public class CityLoader {
	Document doc;
	List<String> allCityNames ;
	List<City> cities;
	List<Card> cityCards;
	List<Card> infectionCards;
	public CityLoader(String _fichier) 
			throws SAXException, ParserConfigurationException, IOException{
		
		FileInputStream _xml_input_file = new FileInputStream(_fichier);
		
		//instancier le contrcuteur de parseurs
		DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();

		//ignorer les commentaires dans les fichiers XML parse
		_factory.setIgnoringComments(true);
		
		// creer un parseur
		DocumentBuilder _builder = _factory.newDocumentBuilder();

		// Charger le document
		this.doc = _builder.parse(_xml_input_file);
		
		doc.getDocumentElement().normalize();
		allCityNames = new ArrayList<>();
		cities = new ArrayList<>();
		cityCards = new ArrayList<>();
		infectionCards = new ArrayList<>();
		
		NodeList  nList = doc.getElementsByTagName("node");
		// Parser le document
		for(int  i = 0 ; i<nList.getLength();i++){
		   	Node  node = nList.item(i);
		   	//out.println("Node name: "+ node.getNodeName());
		   	Element  ele = (Element)node;
		   	if(node.getNodeType() == Element.ELEMENT_NODE){
		   		int id = Integer.parseInt(ele.getAttribute("id"));
		   		String cityName=ele.getElementsByTagName("data").item(0).getTextContent();
		   		//System.out.println("cityName: "+cityName);
		   		City c = new City(id,cityName);
		   		cities.add(c);
		   		allCityNames.add(cityName);
		   		//456
		   		int r = Integer.parseInt(ele.getElementsByTagName("data").item(4).getTextContent());
		   		int g = Integer.parseInt(ele.getElementsByTagName("data").item(5).getTextContent());
		   		int b = Integer.parseInt(ele.getElementsByTagName("data").item(6).getTextContent());
		   		Disease d=null;
		   		if(r==107&&g==112&&b==184) {
		   			d=Disease.BLUE;
		   		}
		   		if(r==242&&g==255&&b==0) {
		   			d=Disease.YELLOW;
		   		}
		   		if(r==153&&g==153&&b==153) {
		   			d=Disease.BLACK;
		   		}
		   		if(r==153&&g==18&&b==21) {
		   			d=Disease.RED;
		   		}
		   		Card card = new Card("city", id, cityName, d);
		   		cityCards.add(card);
		   		Card cardInfection = new Card("infection", id, cityName, d);
		   		infectionCards.add(cardInfection);
		   		//System.out.println("r: "+r+" g: "+g+" b: "+b);
		   		//System.out.println();
		   	}
		}
	}
	public List<City> getCities(){
		return this.cities;
	}
	public List<String> getAllCityNames() 
			throws SAXException, ParserConfigurationException, IOException {
		return allCityNames;
	}
	
	public List<String> neighbours(String cityName) {
		// TODO
		List<String> neighbours = new ArrayList<String>();
		int id = 0;
		for(int i=0;i<allCityNames.size();i++) {
			if(allCityNames.get(i).equals(cityName)) {
				if(i<36) {
					id = i+1;
				}else {
					id=i+4;
				}
			}
		}
		NodeList  nList = doc.getElementsByTagName("edge");
		for(int  i = 0 ; i<nList.getLength();i++){
			 Element element = (Element)nList.item(i);
		   	
			 int sourceId = Integer.parseInt(element.getAttribute("source"));
			 if(id==sourceId) {
				 int targetId = Integer.parseInt(element.getAttribute("target"));
				 if(targetId<=36) {
					 neighbours.add(allCityNames.get(targetId-1));
				 }else {
					 neighbours.add(allCityNames.get(targetId-4));
				 }
			 }
			 
		}
		return neighbours;
		
	}
	public List<Card> getCityCards(){		
		return cityCards;
	}
	public List<Card> getInfectionCards(){
		return infectionCards;
	}
	
	
	public static void main(String[] args) {
		try {
			System.out.println("debut");
			
			String filename = "./pandemic.graphml";
			CityLoader parseur  = new CityLoader(filename);
			
			List<String> allCityNames=parseur.getAllCityNames();
			
			/*
			int i=0;
			for(String item : allCityNames){
				System.out.println(i++);
				System.out.println(item);
				
			}
			System.out.println("fin");
			List<String> neighbours=parseur.neighbours("Karachi");
	        for(String item : neighbours){
				
				
				System.out.println(item);
				
			}
			*/
			/*List<City> cities= new ArrayList<>();
			cities = parseur.getCities();
			for(City c : cities){
				System.out.println(c);
			}*/
			
			List<Card> cards = new ArrayList<>();
			cards = parseur.getCityCards();
			for(Card c : cards){
				//c.setType("");
				System.out.println(c);
			}
			
			System.out.println("fin");
		
		}
		catch (Exception e ){
			e.printStackTrace();
		}
	}
	
}

