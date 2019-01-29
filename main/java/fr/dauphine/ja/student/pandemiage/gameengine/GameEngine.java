package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.dauphine.ja.pandemiage.common.AiInterface;
import fr.dauphine.ja.pandemiage.common.AiLoader;
import fr.dauphine.ja.pandemiage.common.DefeatReason;
import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.GameInterface;
import fr.dauphine.ja.pandemiage.common.GameStatus;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;

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
		
		Scanner in = new Scanner(System.in);
		
		printGameStats();//查看当前状况
		System.err.println("Player is now at "+player.playerLocation());//显示当前player在哪
		System.out.println("Game ready, go? (enter something to start)");
		in.next();//用于阻塞, 玩家随意输入字符后开始
		// Very basic game loop
		while(gameStatus == GameStatus.ONGOING) {
			System.err.println("Player's neib are :");//显示当前player在哪
			for(String neib: this.neighbours(player.playerLocation())) {
				System.out.println(neib);
			}
			System.out.println("get 0?"+this.neighbours(player.playerLocation()).get(0));

			/*每回合玩家做完四件事，还要抽两张城市卡，手上最多拿九张，多了要丢掉*/
			/*如果是蔓延卡，则发生效果*/
			for(int i=0; i<2; i++) {
				Card c = cards.drawCityCard();
				if(c.getType().equals("epidemic")) {
					//如果是蔓延卡，则再抽一张病毒卡触发感染
					Card drawInfection = cards.drawInfectionCard();
					/*感染*/
					System.err.println("GOT INFECTION!! ");//提示受到了感染
					System.err.println("GOT INFECTION!! ");//提示受到了感染
					System.err.println("GOT INFECTION!! ");//提示受到了感染
					cityStates.addInfectionLevel(drawInfection.getCityName(), drawInfection.getDisease(), this.infectionRate());
					
				}
				else {
					//如果是城市卡，则正常抽牌
					player.draw(c);
				}
			}
			/*try {
				player.moveTo(this.neighbours(player.playerLocation()).get(0));	
			}
			catch (UnauthorizedActionException e) {
				e.printStackTrace();
			}*/
			ai.playTurn(this, player);//傻ai总会在旧金山是因为他走了四步总会走回来
			
			System.out.println("GameStatus now is: ");
			System.err.println("Result: " + gameStatus);
			printGameStats();//查看当前状况
			System.err.println("Player is now at "+player.playerLocation());//显示当前player在哪
			
			in.next();//用于阻塞每一回合
			
			/*if(Math.random() < 0.5)
				setDefeated("Game not implemented.", DefeatReason.UNKN);
			else
				setVictorious();	*/
			
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
