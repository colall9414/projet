package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dauphine.ja.pandemiage.common.AiInterface;
import fr.dauphine.ja.pandemiage.common.AiLoader;
import fr.dauphine.ja.pandemiage.common.DefeatReason;
import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.GameInterface;
import fr.dauphine.ja.pandemiage.common.GameStatus;

/**
 * Empty GameEngine implementing GameInterface
 *
 */
public class GameEngine implements GameInterface{

	private final String aiJar;
	private final String cityGraphFilename; 	
	private GameStatus gameStatus;
	
	//new
	private CityLoader cl;
	private int countInfection;//抽到蔓延卡的次数

	// Do not change!
	private void setDefeated(String msg, DefeatReason dr) {		
		gameStatus = GameStatus.DEFEATED;
		System.err.println("Player(s) have been defeated: " + msg);
		System.err.println("Result: " + gameStatus);
		System.err.println("Reason: " + dr);
		printGameStats();
		System.exit(2);
	}

	// Do not change!
	private void setVictorious() {
		gameStatus = GameStatus.VICTORIOUS;
		System.err.println("Player(s) have won.");
		System.err.println("Result: " + gameStatus);
		printGameStats();
		System.exit(0);
	}

	// Do not change!
	private void printGameStats() {
		Map<Disease, Integer> blocks = new HashMap<>();
		for(String city : allCityNames()) {
			for(Disease d : Disease.values()) {
				blocks.put(d, blocks.getOrDefault(city, 0) + infectionLevel(city, d));
			}
		}
		System.err.println(blocks);
		System.err.println("Infection-rate:"+infectionRate());
		for(Disease d : Disease.values()) {
			System.err.println("Cured-" + d + ":"+isCured(d));
		}
		System.err.println("Nb-outbreaks:"+getNbOutbreaks());
		System.err.println("Nb-player-cards-left:"+getNbPlayerCardsLeft());
	}

	public GameEngine(String cityGraphFilename, String aiJar){
		this.cityGraphFilename = cityGraphFilename; 
		this.aiJar = aiJar; 
		this.gameStatus = GameStatus.ONGOING;


		/* ... */

	}


	public void loop()  {
		// Load Ai from Jar file
		System.out.println("Loading AI Jar file " + aiJar);		
		AiInterface ai = AiLoader.loadAi(aiJar);
		//load city file
		try {
			System.out.println("loading cities...");	
			this.cl = new CityLoader(cityGraphFilename);
			//List<String> allCityNames=cl.getAllCityNames();
		}
		catch (Exception e ){
			e.printStackTrace();
		}
		//初始化游戏
		countInfection=0;
		
	
		// Very basic game loop
		while(gameStatus == GameStatus.ONGOING) {
			//ai.playTurn(this, p);
			if(Math.random() < 0.5)
				setDefeated("Game not implemented.", DefeatReason.UNKN);
			else
				setVictorious();			
		}
	}						

	@Override
	public List<String> allCityNames() {
		// TODO
		try {
			return cl.getAllCityNames();
		}
		catch (Exception e ){
			e.printStackTrace();
		}
		throw new UnsupportedOperationException(); 
	}

	@Override
	public List<String> neighbours(String cityName) {
		// TODO
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int infectionLevel(String cityName, Disease d) {
		// TODO
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean isCured(Disease d) {
		// TODO
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int infectionRate() {
		// TODO
		//222 33 44
		if(this.countInfection<=2) {
			return 2;
		}
		else if(this.countInfection<=4){
			return 3;
		}
		else {
			return 4;
		}
		//throw new UnsupportedOperationException(); 
	}

	@Override
	public GameStatus gameStatus() {
		// TODO
		throw new UnsupportedOperationException(); 
	}

	@Override 
	public int turnDuration() {
		// TODO
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean isEradicated(Disease d) {
		// TODO
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getNbOutbreaks() {
		// TODO 
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getNbPlayerCardsLeft() {
		// TODO 
		throw new UnsupportedOperationException(); 
	}
	
}
