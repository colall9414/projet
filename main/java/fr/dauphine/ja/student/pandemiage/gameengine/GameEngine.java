package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	private Cards cards;//Undrawn card
	private int countInfection;//The number of times the epidemic card was drawed
	private CityStates cityStates; //City status
	private DiseaseStates diseaseStates; //All virus global status
	private int nbOutbreaks; //Number of virus outbreaks
	private int turnDuration;//Give ai judgment time per round

	
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


		/* ... */
		//load city file
		try {
			System.out.println("loading cities...");	
			this.cl = new CityLoader(cityGraphFilename);
			//List<String> allCityNames=cl.getAllCityNames();
		}
		catch (Exception e ){
			e.printStackTrace();
		}
		//Initialize the game
		turnDuration = 3;//ai Judging time
		countInfection = 0;
		nbOutbreaks = 0;
		this.cityStates = new CityStates(cl);
		this.diseaseStates = new DiseaseStates();
		this.cards = new Cards(cl);
		//Initialize the player
		player = new Player(this);
	
		/*
		 * In the beginning, first take three virus cards,
		 * Then put the three color virus squares (a total of nine squares) in the three cities.
		 * 2, then draw three more virus cards, this time put two virus;
		 * 3, draw another three virus cards, this time put a virus. */
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				Card c = cards.drawInfectionCard();
				cityStates.addInfectionLevel(c.getCityName(), c.getDisease(), (3-j));
			}
		}
			
		//Take five city cards
		for(int i=0;i<5;i++) {
			player.draw(cards.drawCityCard());
		}

	}


	public void loop()  {
		// Load Ai from Jar file
		System.out.println("Loading AI Jar file " + aiJar);		
		AiInterface ai = AiLoader.loadAi(aiJar);
		
		Scanner in = new Scanner(System.in);
		
		printGameStats();//View current status
		System.err.println("Player is now at "+player.playerLocation());//显示当前player在哪
		System.out.println("Game ready, go? (enter something to start)");
		in.next();//Used for blocking, the player starts typing arbitrarily characters 
		// Very basic game loop
		while(gameStatus == GameStatus.ONGOING) {
			System.out.println("Current place: "+this.neighbours(player.playerLocation()).get(0));//Show where the current player is

			System.err.println("Player's neib are :");
			for(String neib: this.neighbours(player.playerLocation())) {
				System.out.println(neib);
			}
			
			/*every round player will draw 2 cards，do 4actions and then drawing the corresponding number of infected virus cards
			 *  Take up to nine in your hand, throw away if cards on hand >9
			 * 
			/*If it is a epidemic card, the effect will occur*/
			//step 1: draw cards
			int nbEpidemicCards=0;
			for(int i=0; i<2; i++) {
				Card c = cards.drawCityCard();
				if(c.getType().equals("epidemic")) {
					//f it is a epidemic card, then draw another virus card
					Card drawInfection = cards.drawInfectionCard();
					/*infection感染*/
					System.err.println("GOT Epidemic!! ");//Prompt infected
					System.err.println("GOT Epidemic!! ");//Prompt infected
					System.err.println("GOT Epidemic!! ");//Prompt infected
					cityStates.addInfectionLevel(drawInfection.getCityName(), drawInfection.getDisease(), 3);
					nbEpidemicCards++;
					countInfection++;
					
				}
				else {
					//if it is a city card, then the normal draw
					player.draw(c);
				}
				//If the numbre of card is larger than 9, you need to throw it.
				if(player.getNbPlayerCardsLeft()>9) {
					//ai need to discard
					ai.discard(this, player, 9, nbEpidemicCards);
				}
			}
			//step 2: play turn
			ai.playTurn(this, player);//stupid ai will always be in San Francisco because he will walk back four steps.
			
			
			//step 3: play as infecteur
			System.out.println("jouer l'infecteur: ");
			int nbcards = infectionRate();
			System.out.println("draw infection card: " +nbcards);
			for(int i=0;i<nbcards;i++) {
				//check if the disease is eradicated
				Card c = cards.drawInfectionCard();
				if(this.isEradicated(c.getDisease())==true) {
					continue;//if is eradicated do nothing
				}
				//otherwise add infectionlevel to the city of card
				cityStates.addInfectionLevel(c.getCityName(), c.getDisease(), 1);
			}
			//check if we win
			boolean flag_win=true;
			for(DiseaseState d : diseaseStates.getLdise()) {
				if(d.isCured==false) {
					flag_win=false;
				}
			}
			diseaseStates.showDiseaseStates();
			if(flag_win==true) {
				setVictorious();
			}
			
			//check if we lose
			//if threre's no more disease cube(no more infection card) 
			if(cards.getInfectionCards().size()==0) {
				setDefeated("",DefeatReason.NO_MORE_BLOCKS);
			}
			//more than 8 outbreaks
			if(this.getNbOutbreaks()>=8) {
				setDefeated("",DefeatReason.TOO_MANY_OUTBREAKS);
			}
			//no more city card
			if(cards.getCityCards().size()==0) {
				setDefeated("",DefeatReason.NO_MORE_PLAYER_CARDS);
			}
			
			System.out.println("GameStatus now is: ");
			System.err.println("Result: " + gameStatus);
			printGameStats();//View current status
			System.err.println("Player is now at "+player.playerLocation());//显示当前player在哪
			
			
			
			/*if(Math.random() < 0.5)
				setDefeated("Game not implemented.", DefeatReason.UNKN);
			else
				setVictorious();	*/
			
			in.next();//Used to block each round
			
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
		//Check for antidote
		if(diseaseStates.isCured(d)==false) {
			return false;
		}
		boolean flag=true;
		for(CityState cs : cityStates.statesArray) {
			if(cs.getAffectionLevel(d)!=0) {
				flag=false;
			}
		}
		return flag;
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
	
	public void treatDisease(Disease d) {
		if(isCured(d)) {
			//if it is cured
			cityStates.cubeToZero(d,player.playerLocation());
		}
		else {
			cityStates.minusUnCube(d,player.playerLocation());
		}
		
	}
	public void discoverCure(Disease d) {
		diseaseStates.discoverCure(d);
	}
	
}