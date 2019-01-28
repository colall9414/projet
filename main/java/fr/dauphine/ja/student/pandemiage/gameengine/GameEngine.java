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
	private Cards cards;//还未抽的卡
	private int countInfection;//抽到蔓延卡的次数
	private CityStates cityStates; //城市状态
	private DiseaseStates diseaseStates; //所有病毒全局状态
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
		this.cityStates = new CityStates(cl);
		this.diseaseStates = new DiseaseStates();
		this.cards = new Cards(cl);
		//初始化玩家
		player = new Player();
		//给玩家发牌
		/*
		 * for(int i=0; i<4; i++){
		 * 		player.draw(card);
		 * }
		 **/
		/*
		 * 一开始，先抽三张病毒卡，
		 * 然后在这三个城市放上该颜色三个该颜色病毒方块(共九个方块), 
		 * 2，再抽三张病毒卡，这次放两个方块; 
		 * 3，再抽三张病毒卡，这次放一个方块。*/
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				Card c = cards.drawInfectionCard();
				cityStates.addInfectionLevel(c.getCityName(), c.getDisease(), (3-j));
			}
		}
			
		//抽五张城市卡
		for(int i=0;i<5;i++) {
			player.draw(cards.drawCityCard());
		}

	}


	public void loop()  {
		// Load Ai from Jar file
		System.out.println("Loading AI Jar file " + aiJar);		
		AiInterface ai = AiLoader.loadAi(aiJar);
		
		// Very basic game loop
		while(gameStatus == GameStatus.ONGOING) {
			/*每回合玩家做完四件事，还要抽两张城市卡，手上最多拿九张，多了要丢掉*/
			/*如果是蔓延卡，则发生效果*/
			for(int i=0; i<2; i++) {
				Card c = cards.drawCityCard();
				if(c.getType().equals("epidemic")) {
					//如果是蔓延卡，则再抽一张病毒卡触发感染
					City city = cards.drawInfectionCard().getCity();
					/*感染*/
					
				}
				else {
					//如果是城市卡，则正常抽牌
					player.draw(c);
				}
			}
			ai.playTurn(this, player);
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
		return cityStates.getInfectionLevel(cityName, d);
	}

	@Override
	public boolean isCured(Disease d) {
		// TODO
		return diseaseStates.isCured(d);
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
		return diseaseStates.isEradicated(d);
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
