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
	private Player player;
	private CityLoader cl;
	private int countInfection;//抽到蔓延卡的次数
	private CityStates css; //城市状态
	private DiseaseStates dss; //所有病毒全局状态
	private int nbOutbreaks; //病毒爆发次数
	private int turnDuration;//没回合最长持续时间(如果ai在规定时间内未作出判断则跳过？我猜是这样)

	
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
		turnDuration = 3;//3秒判断时间
		countInfection = 0;
		nbOutbreaks = 0;
		this.css = new CityStates(cl);
		this.dss = new DiseaseStates();
		//初始化玩家
		player = new Player();
		//给玩家发牌
		/*
		 * for(int i=0; i<4; i++){
		 * 		player.draw(card);
		 * }
		 **/
	
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
		return cl.neighbours(cityName);
	}

	@Override
	public int infectionLevel(String cityName, Disease d) {
		// TODO
		return css.getInfectionLevel(cityName, d);
	}

	@Override
	public boolean isCured(Disease d) {
		// TODO
		return dss.isCured(d);
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
	}

	@Override
	public GameStatus gameStatus() {
		// TODO
		return this.gameStatus;
		//throw new UnsupportedOperationException(); 
	}

	@Override 
	public int turnDuration() {
		// TODO
		return turnDuration;
		//throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean isEradicated(Disease d) {
		// TODO
		return dss.isCured(d);
		//throw new UnsupportedOperationException(); 
	}

	@Override
	public int getNbOutbreaks() {
		// TODO 
		return this.nbOutbreaks;
		//throw new UnsupportedOperationException(); 
	}

	@Override
	public int getNbPlayerCardsLeft() {
		// TODO 
		return player.getNbPlayerCardsLeft();
		//throw new UnsupportedOperationException(); 
	}
	
}
