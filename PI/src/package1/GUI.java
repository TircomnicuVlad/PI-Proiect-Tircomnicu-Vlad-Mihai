package package1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.util.*;

public class GUI extends JFrame {
	
	//randomizer for cards
	Random rand = new Random();
	
	//temporary integer used for used status
	int tempC;
	
	//list of cards
	ArrayList<Card> Cards = new ArrayList<Card>();
	
	//list of messages
	ArrayList<Message> Log = new ArrayList<Message>();
	
	//fonts used
	Font fontCard = new Font("Times New Roman", Font.PLAIN, 40);
	Font fontQuest = new Font("Times New Roman", Font.BOLD, 40);
	Font fontButton = new Font("Times New Roman", Font.PLAIN, 25);
	Font fontLog = new Font("Times New Roman", Font.ITALIC, 30);
	
	//Log message colors
	Color cDealer = Color.red;
	Color cPlayer = new Color(25,55,255);
	
	//strings used
	String questHitStay = new String("Hit or Stay?");
	String questPlayMore = new String("Play more?");
	
	//colors used
	Color colorBackground = new Color(105,105,105);
	Color colorButton = new Color(192,192,192);
	
	//buttons used
	JButton bHit = new JButton();
	JButton bStay = new JButton();
	JButton bYes = new JButton();
	JButton bNo = new JButton();
	JButton[] bMinus;
	JButton[] bPlus;
	
	//screen resolution
	int sW = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int sH = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	//window resolution
	int aW = 1300;
	int aH = 800;
	
	//card grid position and dimensions
	int gridX = 50;
	int gridY = 50;
	int gridW = 900;
	int gridH = 400;
	
	//card spacing and dimensions
	int spacing = 10;
	int rounding = 10;
	int tCardW = (int) gridW/6;
	int tCardH = (int) gridH/2;
	int cardW = tCardW - spacing*2;
	int cardH = tCardH - spacing*2;
	
	//booleans about phases
	boolean hit_stay_q = true;
	boolean dealer_turn = false;
	boolean play_more_q = false;
	boolean p_stay = false;
	boolean d_stay = false;
	boolean pc1 = false;
	boolean pc2 = false;
	boolean pc3 = false;
	boolean pc4 = false;
	boolean dc1 = false;
	boolean dc2 = false;
	boolean dc3 = false;
	boolean dc4 = false;
	
	//player and dealer card array
	ArrayList<Card> pCards = new ArrayList<Card>();
	ArrayList<Card> dCards = new ArrayList<Card>();
	ArrayList<Card> pDeck = new ArrayList<Card>();
	ArrayList<Card> dDeck = new ArrayList<Card>();
	
	//player and dealer totals
	int pMinTotal = 0;
	int pMaxTotal = 0;
	int dMinTotal = 0;
	int dMaxTotal = 0;
	
	//polygons for diamond shapes
	int[] polyX = new int[4];
	int[] polyY = new int[4];
	
	public GUI() {
		this.setTitle("Blackjack");
		this.setBounds((sW-aW-6)/2, (sH-aH-29)/2, aW+6, aH+29);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		Board board = new Board();
		this.setContentPane(board);
		board.setLayout(null);
		
		
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		Click click = new Click();
		this.addMouseListener(click);
		
		//button stuff
		
		ActHit actHit = new ActHit();
		bHit.addActionListener(actHit);
		bHit.setBounds(1020, 200, 100, 50);
		bHit.setBackground(colorButton);
		bHit.setFont(fontButton);
		bHit.setText("HIT");
		board.add(bHit);
		
		ActStay actStay = new ActStay();
		bStay.addActionListener(actStay);
		bStay.setBounds(1150, 200, 100, 50);
		bStay.setBackground(colorButton);
		bStay.setFont(fontButton);
		bStay.setText("STAY");
		board.add(bStay);
		
		ActYes actYes = new ActYes();
		bYes.addActionListener(actYes);
		bYes.setBounds(1000, 600, 100, 50);
		bYes.setBackground(colorButton);
		bYes.setFont(fontButton);
		bYes.setText("YES");
		board.add(bYes);
		
		ActNo actNo = new ActNo();
		bNo.addActionListener(actNo);
		bNo.setBounds(1150, 600, 100, 50);
		bNo.setBackground(colorButton);
		bNo.setFont(fontButton);
		bNo.setText("NO");
		board.add(bNo);
		
		//creating all cards
		
		String temp_str = "starting_temp_str_name";
		for (int i = 0; i <= 40; i++) {
			if (i % 4 == 0) {
				temp_str = "Spades";
			} else if (i % 4 == 1) {
				temp_str = "Hearts";
			} else if (i % 4 == 2) {
				temp_str = "Diamonds";
			} else if (i % 4 == 3) {
				temp_str = "Clubs";
			}
			Cards.add(new Card((i/4) + 1, temp_str, i));
		}
		/*
		System.out.println("---ooo---ooo---ooo---");
		System.out.println("Creating cards finished!");
		System.out.println("---ooo---ooo---ooo---");
		*/
		//randomly selecting initial cards for player and dealer
		
		tempC = rand.nextInt(40);
		pCards.add(Cards.get(tempC));
		Cards.get(tempC).setUsed();
	//	System.out.println("Card " + pCards.get(0).name + " of " + pCards.get(0).shape + " added to the player's cards.");
		
		tempC = rand.nextInt(40);
		while (Cards.get(tempC).used == true) {
			tempC = rand.nextInt(40);
		}
		dCards.add(Cards.get(tempC));
		Cards.get(tempC).setUsed();
	//	System.out.println("Card " + dCards.get(0).name + " of " + dCards.get(0).shape + " added to the dealer's cards.");
		
		tempC = rand.nextInt(40);
		while (Cards.get(tempC).used == true) {
			tempC = rand.nextInt(40);
		}
		pCards.add(Cards.get(tempC));
		Cards.get(tempC).setUsed();
	//	System.out.println("Card " + pCards.get(1).name + " of " + pCards.get(1).shape + " added to the player's cards.");
		
		tempC = rand.nextInt(40);
		while (Cards.get(tempC).used == true) {
			tempC = rand.nextInt(40);
		}
		dCards.add(Cards.get(tempC));
		Cards.get(tempC).setUsed();
		
		tempC = rand.nextInt(40);
		for(int i=0; i<4; i++) {
			while (Cards.get(tempC).used == true) {
				tempC = rand.nextInt(40);
			}
			pDeck.add(Cards.get(tempC));
			Cards.get(tempC).setUsed();
		}
		
		tempC = rand.nextInt(40);
		for(int i=0; i<4; i++) {
			while (Cards.get(tempC).used == true) {
				tempC = rand.nextInt(40);
			}
			dDeck.add(Cards.get(tempC));
			Cards.get(tempC).setUsed();
		}
	//	System.out.println("Card " + dCards.get(1).name + " of " + dCards.get(1).shape + " added to the dealer's cards.");
		/*
		System.out.println("---ooo---ooo---ooo---");
		System.out.println("Setting cards finished!");
		System.out.println("---ooo---ooo---ooo---");
		*/
	}
	
	public void totalsChecker() {
		
		int acesCount;
		
		//calculation of player's totals
		pMinTotal = 0;
		pMaxTotal = 0;
		acesCount = 0;
		
		for (Card c : pCards) {
			pMinTotal += c.value;
			pMaxTotal += c.value;
			if (c.name == "Ace")
				acesCount++;
			
		}
		
		if (acesCount > 0)
			pMaxTotal += 10;
		
		dMinTotal = 0;
		dMaxTotal = 0;
		acesCount = 0;
		
		for (Card c : dCards) {
			dMinTotal += c.value;
			dMaxTotal += c.value;
			if (c.name == "Ace")
				acesCount++;
			
		}
		
		if (acesCount > 0)
			dMaxTotal += 10;
	}
	
	public void setWinner() {
		int pPoints = 0;
		int dPoints = 0;
		
		if (pMaxTotal > 20) {
			pPoints = pMinTotal;
		} else {
			pPoints = pMaxTotal;
		}
		
		if (dMaxTotal > 20) {
			dPoints = dMinTotal;
		} else {
			dPoints = dMaxTotal;
		}
		
		if ((pPoints > 20 && dPoints > 20) || pPoints == dPoints) {
			Log.add(new Message("Nobody wins!", ""));
		} else if (dPoints > 20) {
			Log.add(new Message("Player 1 win!", "Player"));
			Main.pWins++;
		} else if (pPoints > 20) {
			Log.add(new Message("Player 2 wins!", "Dealer"));
			Main.dWins++;
		} else if (pPoints > dPoints) {
			Log.add(new Message("Player 1 win!", "Player"));
			Main.pWins++;
		} else {
			Log.add(new Message("Player 2 wins!", "Dealer"));
			Main.dWins++;
		}
		
		d_stay = false;
		p_stay = false;
		
	}
	
	public void refresher() {
		
		if (hit_stay_q == true) {
			bHit.setVisible(true);
			bStay.setVisible(true);
		} else {
			bHit.setVisible(false);
			bStay.setVisible(false);
		}
		
		if (play_more_q == true) {
			bYes.setVisible(true);
			bNo.setVisible(true);
		} else {
			bYes.setVisible(false);
			bNo.setVisible(false);
		}
		
		totalsChecker();
		
		if ((pMaxTotal == 20 || pMinTotal >= 20) && hit_stay_q == true && dealer_turn == false) {
			int tempMax = 0;
			if (pMaxTotal <= 20) {
				tempMax = pMaxTotal;
			} else {
				tempMax = pMinTotal;
			}
			String mess = ("Player 1 auto pass! (total: " + Integer.toString(tempMax) + ")");
			Log.add(new Message(mess, "Player 1"));
			hit_stay_q = false;
			play_more_q = true;
			p_stay = true;
			setWinner();
		}
		
		if ((dMaxTotal == 20 || dMinTotal >= 20) && dealer_turn == true) {
			int tempMax = 0;
			if (dMaxTotal <= 20) {
				tempMax = dMaxTotal;
			} else {
				tempMax = dMinTotal;
			}
			String mess = ("Player 2 auto pass! (total: " + Integer.toString(tempMax) + ")");
			Log.add(new Message(mess, "Player 2"));
			dealer_turn = false;
			hit_stay_q = false;
			play_more_q = true;
			d_stay = true;
			setWinner();
		}
		
		repaint();
	}
	
	public class Board extends JPanel {
		
		public void paintComponent(Graphics g) {
			//background
			g.setColor(colorBackground);
			g.fillRect(0, 0, aW, aH);
			
			//questions
			if (hit_stay_q == true) {
				g.setColor(Color.black);
				g.setFont(fontQuest);
				g.drawString(questHitStay, gridX+gridW+60, gridY+90);
				g.drawString("Total:", gridX+gridW+60, gridY+290);
			} else if (play_more_q == true) {
				g.setColor(Color.black);
				g.setFont(fontQuest);
				g.drawString(questPlayMore, gridX+gridW+70, gridY+490);
			}
		
			g.setColor(Color.pink);
			g.drawRect(gridX-10, gridY-10, gridW/2+10, gridH+ gridH/2+20);
			//g.drawLine(gridX-10,(gridY+gridH+50)/2,gridX+gridW+10,(gridY+gridH+50)/2);
			g.drawRect(gridX-10+gridW/2+10, gridY-10, gridW/2+10, gridH+ gridH/2+20);
			//g.drawLine(gridX-10,(gridY+gridH+50)/2+gridH/2,gridX+gridW+10,(gridY+gridH+50)/2+gridH/2);
			/*for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 2; j++) {
					g.drawRect(gridX+spacing+tCardW*i, gridY+spacing+tCardH*j, cardW, cardH);
				}
			}*/
			g.drawRect(gridX+gridW+50, gridY-10, 270, 420);
			g.drawRect(gridX-10, gridY+gridH+220, gridW+19, 250);
			
			g.setColor(Color.black);
			g.fillRect(gridX-10, gridY+gridH+220, gridW+22, 500);
			
			//Log
			/*g.setFont(fontLog);
			int logIndex = 0;
			for (Message L : Log) {
				if (L.getWho().equalsIgnoreCase("Dealer")) {
					g.setColor(cDealer);
				} else {
					g.setColor(cPlayer);
				}
				g.drawString(L.getMessage(), gridX+20, gridY+480+logIndex*35);
				logIndex++;
			}*/
			
			//score
			g.setColor(Color.BLACK);
			g.setFont(fontQuest);
			String score = ("Score: " + Integer.toString(Main.pWins) + " - " + Integer.toString(Main.dWins));
			g.drawString(score, gridX+gridW+70, gridY+gridH+300);
			
			//player cards
			int i = 0;
			int index = 0;
			int indexy = -1;
			for (Card c : pCards) {
				if(i%3==0) {
					index = 0 ;
					indexy++;
				}
				if(i==8) {
					p_stay = true;
					if(d_stay == false) {
						dealer_turn = true;
					}else {
						refresher();
						setWinner();
						hit_stay_q = false;
						play_more_q = true;
						break;
					}
				}
				g.setColor(Color.WHITE);
				g.fillRect(gridX+spacing+tCardW*index+rounding, gridY+spacing + (cardH+20)*indexy, cardW-rounding*2, cardH);
				g.fillRect(gridX+spacing+tCardW*index, gridY+spacing+rounding+ (cardH+20)*indexy, cardW, cardH-rounding*2);
				g.fillOval(gridX+spacing+tCardW*index, gridY+spacing+ (cardH+20)*indexy, rounding*2, rounding*2);
				g.fillOval(gridX+spacing+tCardW*index, gridY+spacing+cardH-rounding*2+ (cardH+20)*indexy, rounding*2, rounding*2);
				g.fillOval(gridX+spacing+tCardW*index+cardW-rounding*2, gridY+spacing+ (cardH+20)*indexy, rounding*2, rounding*2);
				g.fillOval(gridX+spacing+tCardW*index+cardW-rounding*2, gridY+spacing+cardH-rounding*2+ (cardH+20)*indexy, rounding*2, rounding*2);
				
				
				g.setColor(Color.GREEN);
				g.fillRect(gridX+20+tCardW*index, gridY+30+ (cardH+20)*indexy, cardW-rounding*2, cardH-100);
				g.fillRect(gridX+30+tCardW*index, gridY+20+ (cardH+20)*indexy, cardW-rounding*2-20, cardH-80);
				g.fillOval(gridX+20+tCardW*index, gridY+20+ (cardH+20)*indexy, rounding*2, rounding*2);
				g.fillOval(gridX+20+tCardW*index, gridY+20+cardH-100+ (cardH+20)*indexy, rounding*2, rounding*2);
				g.fillOval(gridX+20+tCardW*index+cardW-rounding*2-20, gridY+20+cardH-100+ (cardH+20)*indexy, rounding*2, rounding*2);
				
				
				g.fillOval(gridX+tCardW*index+cardW-rounding*2+5, gridY+spacing+5+ (cardH+20)*indexy, rounding + 10, rounding+10);
				g.fillRect(gridX+20+tCardW*index+rounding, gridY+cardH-20+ (cardH+20)*indexy, (cardW-rounding*2)/2, 30);
				g.fillRect(gridX+20+tCardW*index, gridY+cardH-10+ (cardH+20)*indexy, (cardW-rounding*2)/2+rounding, 20);
				g.fillOval(gridX+20+tCardW*index, gridY+cardH-20+ (cardH+20)*indexy, rounding*2, rounding*2);
				g.fillRect(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding, gridY+cardH-20+ (cardH+20)*indexy, (cardW-rounding*2)/2-rounding-rounding, 30);
				g.fillRect(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding, gridY+cardH-10+ (cardH+20)*indexy, (cardW-rounding*2)/2-rounding, 20);
				g.fillOval(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding*4-5,gridY+cardH-20+ (cardH+20)*indexy, rounding*2, rounding*2);
				g.setColor(Color.BLACK);
				g.fillRect(gridX+20+tCardW*index, gridY+50+ (cardH+20)*indexy, cardW-rounding*2, cardH/5);
				g.setColor(Color.WHITE);
				g.fillPolygon(new int[] {gridX+55+tCardW*index, gridX+tCardW*index+75, gridX+20+tCardW*index+75}, new int[] {gridY+50+ (cardH+20)*indexy, gridY+rounding+21+ (cardH+20)*indexy, gridY+50+ (cardH+20)*indexy}, 3);
				g.fillPolygon(new int[] {gridX+55+tCardW*index, gridX+tCardW*index+75, gridX+20+tCardW*index+75}, new int[] {gridY+86+ (cardH+20)*indexy, gridY+rounding+95+ (cardH+20)*indexy, gridY+86+ (cardH+20)*indexy}, 3);
				g.drawString(c.symbol, gridX+tCardW*index+65, gridY+cardH-100+ (cardH+20)*indexy);
				
				if(i == 8) {
					break;
				}
				
				/*if (c.shape.equalsIgnoreCase("Hearts")) {
					g.fillOval(gridX+tCardW*index+42, gridY+70, 35, 35);
					g.fillOval(gridX+tCardW*index+73, gridY+70, 35, 35);
					g.fillArc(gridX+tCardW*index+30, gridY+90, 90, 90, 51, 78);
				} else if (c.shape.equalsIgnoreCase("Diamonds")) {
					polyX[0] = gridX+tCardW*index+75;
					polyX[1] = gridX+tCardW*index+50;
					polyX[2] = gridX+tCardW*index+75;
					polyX[3] = gridX+tCardW*index+100;
					polyY[0] = gridY+60;
					polyY[1] = gridY+100;
					polyY[2] = gridY+140;
					polyY[3] = gridY+100;
					g.fillPolygon(polyX, polyY, 4);
				} else if (c.shape.equalsIgnoreCase("Spades")) {
					g.fillOval(gridX+tCardW*index+42, gridY+90, 35, 35);
					g.fillOval(gridX+tCardW*index+73, gridY+90, 35, 35);
					g.fillArc(gridX+tCardW*index+30, gridY+15, 90, 90, 51+180, 78);
					g.fillRect(gridX+tCardW*index+70, gridY+100, 10, 40);
				} else {
					g.fillOval(gridX+tCardW*index+40, gridY+90, 35, 35);
					g.fillOval(gridX+tCardW*index+75, gridY+90, 35, 35);
					g.fillOval(gridX+tCardW*index+58, gridY+62, 35, 35);
					g.fillRect(gridX+tCardW*index+70, gridY+75, 10, 70);
				}*/
				
				//-------------------------
				i++;
				index++;
			}
			
				//dealer cards
				i = 0;
				index = 0;
				indexy = -1;
				for (Card c : dCards) {
					if(i%3==0) {
						index = 0 ;
						indexy++;
					}

					
					g.setColor(Color.WHITE);
					g.fillRect(gridX+spacing+tCardW*index+rounding + 3*tCardW, gridY+spacing+ (cardH+20)*indexy, cardW-rounding*2, cardH);
					g.fillRect(gridX+spacing+tCardW*index+ 3*tCardW, gridY+spacing+rounding+ (cardH+20)*indexy, cardW, cardH-rounding*2);
					g.fillOval(gridX+spacing+tCardW*index+ 3*tCardW, gridY+spacing+ (cardH+20)*indexy, rounding*2, rounding*2);
					g.fillOval(gridX+spacing+tCardW*index+ 3*tCardW, gridY+spacing+cardH-rounding*2+ (cardH+20)*indexy, rounding*2, rounding*2);
					g.fillOval(gridX+spacing+tCardW*index+ 3*tCardW+cardW-rounding*2, gridY+spacing+ (cardH+20)*indexy, rounding*2, rounding*2);
					g.fillOval(gridX+spacing+tCardW*index+ 3*tCardW+cardW-rounding*2, gridY+spacing+cardH-rounding*2+ (cardH+20)*indexy, rounding*2, rounding*2);
					
					
					g.setColor(Color.GREEN);
					g.fillRect(gridX+20+tCardW*index+ 3*tCardW, gridY+30+ (cardH+20)*indexy, cardW-rounding*2, cardH-100);
					g.fillRect(gridX+30+tCardW*index+ 3*tCardW, gridY+20+ (cardH+20)*indexy, cardW-rounding*2-20, cardH-80);
					g.fillOval(gridX+20+tCardW*index+ 3*tCardW, gridY+20+ (cardH+20)*indexy, rounding*2, rounding*2);
					g.fillOval(gridX+20+tCardW*index+ 3*tCardW, gridY+20+cardH-100+ (cardH+20)*indexy, rounding*2, rounding*2);
					g.fillOval(gridX+20+tCardW*index+ 3*tCardW+cardW-rounding*2-20, gridY+20+cardH-100+ (cardH+20)*indexy, rounding*2, rounding*2);
					
					
					g.fillOval(gridX+tCardW*index+cardW-rounding*2+5+ 3*tCardW, gridY+spacing+5+ (cardH+20)*indexy, rounding + 10, rounding+10);
					g.fillRect(gridX+20+tCardW*index+rounding+ 3*tCardW, gridY+cardH-20+ (cardH+20)*indexy, (cardW-rounding*2)/2, 30);
					g.fillRect(gridX+20+tCardW*index+ 3*tCardW, gridY+cardH-10+ (cardH+20)*indexy, (cardW-rounding*2)/2+rounding, 20);
					g.fillOval(gridX+20+tCardW*index+ 3*tCardW, gridY+cardH-20+ (cardH+20)*indexy, rounding*2, rounding*2);
					g.fillRect(gridX+20+tCardW*index+ 3*tCardW+(cardW-rounding*2)/2+rounding, gridY+cardH-20+ (cardH+20)*indexy, (cardW-rounding*2)/2-rounding-rounding, 30);
					g.fillRect(gridX+20+tCardW*index+ 3*tCardW+(cardW-rounding*2)/2+rounding, gridY+cardH-10+ (cardH+20)*indexy, (cardW-rounding*2)/2-rounding, 20);
					g.fillOval(gridX+20+tCardW*index+ 3*tCardW+(cardW-rounding*2)/2+rounding*4-5,gridY+cardH-20+ (cardH+20)*indexy, rounding*2, rounding*2);
					g.setColor(Color.BLACK);
					g.fillRect(gridX+20+tCardW*index+ 3*tCardW, gridY+50+ (cardH+20)*indexy, cardW-rounding*2, cardH/5);
					g.setColor(Color.WHITE);
					g.fillPolygon(new int[] {gridX+55+tCardW*index+ 3*tCardW, gridX+tCardW*index+75+ 3*tCardW, gridX+20+tCardW*index+75+ 3*tCardW}, new int[] {gridY+50+ (cardH+20)*indexy, gridY+rounding+21+ (cardH+20)*indexy, gridY+50+ (cardH+20)*indexy}, 3);
					g.fillPolygon(new int[] {gridX+55+tCardW*index+ 3*tCardW, gridX+tCardW*index+75+ 3*tCardW, gridX+20+tCardW*index+75+ 3*tCardW}, new int[] {gridY+86+ (cardH+20)*indexy, gridY+rounding+95+ (cardH+20)*indexy, gridY+86+ (cardH+20)*indexy}, 3);
					g.drawString(c.symbol, gridX+tCardW*index+65+ 3*tCardW, gridY+cardH-100+ (cardH+20)*indexy);
					
					
					if(i == 8) {
						break;
					}
					/*if (c.shape.equalsIgnoreCase("Hearts")) {
						g.fillOval(gridX+tCardW*index+42, gridY+70+200, 35, 35);
						g.fillOval(gridX+tCardW*index+73, gridY+70+200, 35, 35);
						g.fillArc(gridX+tCardW*index+30, gridY+90+200, 90, 90, 51, 78);
					} else if (c.shape.equalsIgnoreCase("Diamonds")) {
						polyX[0] = gridX+tCardW*index+75;
						polyX[1] = gridX+tCardW*index+50;
						polyX[2] = gridX+tCardW*index+75;
						polyX[3] = gridX+tCardW*index+100;
						polyY[0] = gridY+60+200;
						polyY[1] = gridY+100+200;
						polyY[2] = gridY+140+200;
						polyY[3] = gridY+100+200;
						g.fillPolygon(polyX, polyY, 4);
					} else if (c.shape.equalsIgnoreCase("Spades")) {
						g.fillOval(gridX+tCardW*index+42, gridY+90+200, 35, 35);
						g.fillOval(gridX+tCardW*index+73, gridY+90+200, 35, 35);
						g.fillArc(gridX+tCardW*index+30, gridY+15+200, 90, 90, 51+180, 78);
						g.fillRect(gridX+tCardW*index+70, gridY+100+200, 10, 40);
					} else {
						g.fillOval(gridX+tCardW*index+40, gridY+90+200, 35, 35);
						g.fillOval(gridX+tCardW*index+75, gridY+90+200, 35, 35);
						g.fillOval(gridX+tCardW*index+58, gridY+62+200, 35, 35);
						g.fillRect(gridX+tCardW*index+70, gridY+75+200, 10, 70);
					}*/
					
					//-------------------------
					index++;
					i++;
				}
				
				index = 0;
				bMinus = new JButton[5];
				bPlus = new JButton[5];
				ActMinus1 actMinus1 = new ActMinus1();
				ActMinus2 actMinus2 = new ActMinus2();
				ActMinus3 actMinus3 = new ActMinus3();
				ActMinus4 actMinus4 = new ActMinus4();
				ActPlus1 actPlus1 = new ActPlus1();
				ActPlus2 actPlus2 = new ActPlus2();
				ActPlus3 actPlus3 = new ActPlus3();
				ActPlus4 actPlus4 = new ActPlus4();
				if(!dealer_turn) {
					for (Card c : pDeck) {
						g.setColor(Color.WHITE);
						g.fillRect(gridX+spacing+tCardW*index+rounding, gridY+gridH+220+spacing , cardW-rounding*2, cardH);
						g.fillRect(gridX+spacing+tCardW*index, gridY+gridH+220+rounding, cardW, cardH-rounding*2);
						g.fillOval(gridX+spacing+tCardW*index, gridY+gridH+220+spacing, rounding*2, rounding*2);
						g.fillOval(gridX+spacing+tCardW*index, gridY+gridH+220+spacing+cardH-rounding*2, rounding*2, rounding*2);
						g.fillOval(gridX+spacing+tCardW*index+cardW-rounding*2, gridY+gridH+220+spacing, rounding*2, rounding*2);
						g.fillOval(gridX+spacing+tCardW*index+cardW-rounding*2, gridY+gridH+220+spacing+cardH-rounding*2, rounding*2, rounding*2);
						
						
						g.setColor(Color.GREEN);
						g.fillRect(gridX+20+tCardW*index, gridY+gridH+220+30, cardW-rounding*2, cardH-100);
						g.fillRect(gridX+30+tCardW*index, gridY+gridH+220+20, cardW-rounding*2-20, cardH-80);
						g.fillOval(gridX+20+tCardW*index, gridY+gridH+220+20, rounding*2, rounding*2);
						g.fillOval(gridX+20+tCardW*index, gridY+gridH+220+20+cardH-100, rounding*2, rounding*2);
						g.fillOval(gridX+20+tCardW*index+cardW-rounding*2-20, gridY+gridH+220+20+cardH-100, rounding*2, rounding*2);
						
						
						g.fillOval(gridX+tCardW*index+cardW-rounding*2+5, gridY+gridH+220+spacing+5, rounding + 10, rounding+10);
						g.fillRect(gridX+20+tCardW*index+rounding, gridY+gridH+220+cardH-20, (cardW-rounding*2)/2, 30);
						g.fillRect(gridX+20+tCardW*index, gridY+gridH+220+cardH-10, (cardW-rounding*2)/2+rounding, 20);
						g.fillOval(gridX+20+tCardW*index, gridY+gridH+220+cardH-20, rounding*2, rounding*2);
						g.fillRect(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding, gridY+gridH+220+cardH-20, (cardW-rounding*2)/2-rounding-rounding, 30);
						g.fillRect(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding, gridY+gridH+220+cardH-10, (cardW-rounding*2)/2-rounding, 20);
						g.fillOval(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding*4-5,gridY+gridH+220+cardH-20, rounding*2, rounding*2);
						g.setColor(Color.BLACK);
						g.fillRect(gridX+20+tCardW*index, gridY+gridH+220+50, cardW-rounding*2, cardH/5);
						g.setColor(Color.WHITE);
						g.fillPolygon(new int[] {gridX+55+tCardW*index, gridX+tCardW*index+75, gridX+20+tCardW*index+75}, new int[] {gridY+gridH+220+50 , gridY+gridH+220+rounding+21, gridY+gridH+220+50 }, 3);
						g.fillPolygon(new int[] {gridX+55+tCardW*index, gridX+tCardW*index+75, gridX+20+tCardW*index+75}, new int[] {gridY+gridH+220+86, gridY+gridH+220+rounding+95, gridY+gridH+220+86}, 3);
						
						//g.drawString("-", gridX+tCardW*index+25, gridY+cardH-104+gridH+220);
						//g.drawString("+", gridX+tCardW*index+105, gridY+cardH-102+gridH+220);
					
						bMinus[index] = new JButton();
						
						bMinus[index].setBounds(gridX+tCardW*index+20,gridY+cardH-127+gridH+220, 40, cardH/9);
						bMinus[index].setBackground(Color.BLACK);
						bMinus[index].setForeground(Color.WHITE);
						bMinus[index].setFont(new Font("Times New Roman", Font.BOLD, 40));
						bMinus[index].setBorder(getBorder());
						bMinus[index].setText("-");
						
						add(bMinus[index]);
						
						bPlus[index] = new JButton();
						bPlus[index].setBounds( gridX+tCardW*index+90, gridY+cardH-125+gridH+220, 40, cardH/9);
						bPlus[index].setBackground(Color.BLACK);
						bPlus[index].setForeground(Color.WHITE);
						bPlus[index].setFont(new Font("Times New Roman", Font.BOLD, 40));
						bPlus[index].setText("+");
						bPlus[index].setBorder(getBorder());
						add(bPlus[index]);
						if(index == 0 && pc1 == false) {
							bMinus[index].addActionListener(actMinus1);
							bPlus[index].addActionListener(actPlus1);
						} else if (index == 1 && pc2 == false) {
							bMinus[index].addActionListener(actMinus2);
							bPlus[index].addActionListener(actPlus2);
						} else if (index == 2 && pc3 == false) {
							bMinus[index].addActionListener(actMinus3);
							bPlus[index].addActionListener(actPlus3);
						} else if (index == 3 && pc4 == false) {
							bMinus[index].addActionListener(actMinus4);
							bPlus[index].addActionListener(actPlus4);
						}

						g.drawString(c.symbol, gridX+tCardW*index+65, gridY+cardH-100+gridH+220);

						g.setColor(Color.BLACK);
						if(index == 0 && pc1 == true) {
							g.drawString("USED", gridX+tCardW*index+20, gridY+cardH-130+gridH+220);
						} else if (index == 1 && pc2 == true) {
							g.drawString("USED", gridX+tCardW*index+20, gridY+cardH-130+gridH+220);
						} else if (index == 2 && pc3 == true) {
							g.drawString("USED", gridX+tCardW*index+20, gridY+cardH-130+gridH+220);
						} else if (index == 3 && pc4 == true) {
							g.drawString("USED", gridX+tCardW*index+20, gridY+cardH-130+gridH+220);
						}
						
						index++;
					}
				} else {
					for (Card c : dDeck) {
						g.setColor(Color.WHITE);
						g.fillRect(gridX+spacing+tCardW*index+rounding, gridY+gridH+220+spacing , cardW-rounding*2, cardH);
						g.fillRect(gridX+spacing+tCardW*index, gridY+gridH+220+rounding, cardW, cardH-rounding*2);
						g.fillOval(gridX+spacing+tCardW*index, gridY+gridH+220+spacing, rounding*2, rounding*2);
						g.fillOval(gridX+spacing+tCardW*index, gridY+gridH+220+spacing+cardH-rounding*2, rounding*2, rounding*2);
						g.fillOval(gridX+spacing+tCardW*index+cardW-rounding*2, gridY+gridH+220+spacing, rounding*2, rounding*2);
						g.fillOval(gridX+spacing+tCardW*index+cardW-rounding*2, gridY+gridH+220+spacing+cardH-rounding*2, rounding*2, rounding*2);
						
						
						g.setColor(Color.GREEN);
						g.fillRect(gridX+20+tCardW*index, gridY+gridH+220+30, cardW-rounding*2, cardH-100);
						g.fillRect(gridX+30+tCardW*index, gridY+gridH+220+20, cardW-rounding*2-20, cardH-80);
						g.fillOval(gridX+20+tCardW*index, gridY+gridH+220+20, rounding*2, rounding*2);
						g.fillOval(gridX+20+tCardW*index, gridY+gridH+220+20+cardH-100, rounding*2, rounding*2);
						g.fillOval(gridX+20+tCardW*index+cardW-rounding*2-20, gridY+gridH+220+20+cardH-100, rounding*2, rounding*2);
						
						
						g.fillOval(gridX+tCardW*index+cardW-rounding*2+5, gridY+gridH+220+spacing+5, rounding + 10, rounding+10);
						g.fillRect(gridX+20+tCardW*index+rounding, gridY+gridH+220+cardH-20, (cardW-rounding*2)/2, 30);
						g.fillRect(gridX+20+tCardW*index, gridY+gridH+220+cardH-10, (cardW-rounding*2)/2+rounding, 20);
						g.fillOval(gridX+20+tCardW*index, gridY+gridH+220+cardH-20, rounding*2, rounding*2);
						g.fillRect(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding, gridY+gridH+220+cardH-20, (cardW-rounding*2)/2-rounding-rounding, 30);
						g.fillRect(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding, gridY+gridH+220+cardH-10, (cardW-rounding*2)/2-rounding, 20);
						g.fillOval(gridX+20+tCardW*index+(cardW-rounding*2)/2+rounding*4-5,gridY+gridH+220+cardH-20, rounding*2, rounding*2);
						g.setColor(Color.BLACK);
						g.fillRect(gridX+20+tCardW*index, gridY+gridH+220+50, cardW-rounding*2, cardH/5);
						g.setColor(Color.WHITE);
						g.fillPolygon(new int[] {gridX+55+tCardW*index, gridX+tCardW*index+75, gridX+20+tCardW*index+75}, new int[] {gridY+gridH+220+50 , gridY+gridH+220+rounding+21, gridY+gridH+220+50 }, 3);
						g.fillPolygon(new int[] {gridX+55+tCardW*index, gridX+tCardW*index+75, gridX+20+tCardW*index+75}, new int[] {gridY+gridH+220+86, gridY+gridH+220+rounding+95, gridY+gridH+220+86}, 3);
						
						//g.drawString("-", gridX+tCardW*index+25, gridY+cardH-104+gridH+220);
						//g.drawString("+", gridX+tCardW*index+105, gridY+cardH-102+gridH+220);
						bMinus[index] = new JButton();
						bMinus[index].setBounds(gridX+tCardW*index+20,gridY+cardH-127+gridH+220, 40, cardH/9);
						bMinus[index].setBackground(Color.BLACK);
						bMinus[index].setForeground(Color.WHITE);
						bMinus[index].setFont(new Font("Times New Roman", Font.BOLD, 40));
						bMinus[index].setBorder(getBorder());
						//bMinus[index].setText("-");
						
						add(bMinus[index]);
						
						bPlus[index] = new JButton();
						bPlus[index].setBounds( gridX+tCardW*index+90, gridY+cardH-125+gridH+220, 40, cardH/9);
						bPlus[index].setBackground(Color.BLACK);
						bPlus[index].setForeground(Color.WHITE);
						bPlus[index].setFont(new Font("Times New Roman", Font.BOLD, 40));
						bPlus[index].setText("+");
						bPlus[index].setBorder(getBorder());
						add(bPlus[index]);
						if(index == 0 && dc1 == false) {
							bMinus[index].addActionListener(actMinus1);
							bPlus[index].addActionListener(actPlus1);
						} else if (index == 1 && dc2 == false) {
							bMinus[index].addActionListener(actMinus2);
							bPlus[index].addActionListener(actPlus2);
						} else if (index == 2 && dc3 == false) {
							bMinus[index].addActionListener(actMinus3);
							bPlus[index].addActionListener(actPlus3);
						} else if (index == 3 && dc4 == false) {
							bMinus[index].addActionListener(actMinus4);
							bPlus[index].addActionListener(actPlus4);
						} 
						
						g.drawString(c.symbol, gridX+tCardW*index+65, gridY+cardH-100+gridH+220);
						
						g.setColor(Color.BLACK);
						if(index == 0 && dc1 == true) {
							g.drawString("USED", gridX+tCardW*index+20, gridY+cardH-130+gridH+220);
						} else if (index == 1 && dc2 == true) {
							g.drawString("USED", gridX+tCardW*index+20, gridY+cardH-130+gridH+220);
						} else if (index == 2 && dc3 == true) {
							g.drawString("USED", gridX+tCardW*index+20, gridY+cardH-130+gridH+220);
						} else if (index == 3 && dc4 == true) {
							g.drawString("USED", gridX+tCardW*index+20, gridY+cardH-130+gridH+220);
						}
						
						
						
						index++;
					}
				}
				
				
				g.setColor(Color.black);
				g.setFont(fontQuest);
				g.drawString("Players 1 total: ", gridX+gridW+60, gridY+40);
				if (pMaxTotal <= 20) {
					g.drawString(Integer.toString(pMaxTotal), gridX+gridW+60, gridY+120);
				} else {
					g.drawString(Integer.toString(pMinTotal), gridX+gridW+60, gridY+120);
				}
				g.drawString("Players 2 total: ", gridX+gridW+60, gridY+240);
				if (dMaxTotal <= 20) {
					g.drawString(Integer.toString(dMaxTotal), gridX+gridW+60, gridY+320);
				} else {
					g.drawString(Integer.toString(dMinTotal), gridX+gridW+60, gridY+320);
				}
			
			
		}
		
	}
	
	public class Move implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class ActHit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(pCards.size() == 9) {
				p_stay = true;
				dealer_turn = true;
				
				if(d_stay == true) {
					refresher();
					setWinner();
					hit_stay_q = false;
					dealer_turn = false;
					play_more_q = true;
				}
			}
			if(dCards.size() == 9) {
				d_stay = true;
				dealer_turn = false;
				
				if(p_stay == true) {
					refresher();
					setWinner();
					hit_stay_q = false;
					dealer_turn = false;
					play_more_q = true;
				}
			}
			if (hit_stay_q == true && dealer_turn==false && (dCards.size() != 9 || pCards.size() != 9)) {
			//	System.out.println("You clicked 'HIT'");
				
				int tempMax = 0;
				if (pMaxTotal <= 20) {
					tempMax = pMaxTotal;
				} else {
					tempMax = pMinTotal;
				}
				String mess = ("Player 1 decided to hit! (total: " + Integer.toString(tempMax) + ")");
				Log.add(new Message(mess, "Player"));
				
				tempC = rand.nextInt(30);
				while (Cards.get(tempC).used == true) {
					tempC = rand.nextInt(30);
				}
				pCards.add(Cards.get(tempC));
				Cards.get(tempC).setUsed();
				if(d_stay == false) {
					dealer_turn=true;
				}
				if(tempMax>=20) {
					hit_stay_q= false;
					play_more_q=true;
					dealer_turn=false;
					p_stay = true;
					setWinner();
				}
			//	System.out.println("Card " + pCards.get(pCards.size()-1).name + " of " + pCards.get(pCards.size()-1).shape + " added to the player's cards.");
			}
			else if (dealer_turn==true) {
				int tempMax = 0;
				if (dMaxTotal <= 20) {
					tempMax = dMaxTotal;
				} else {
					tempMax = dMinTotal;
				}
				String mess = ("Player 2 decided to hit! (total: " + Integer.toString(tempMax) + ")");
				Log.add(new Message(mess, "Player"));
				
				tempC = rand.nextInt(30);
				while (Cards.get(tempC).used == true) {
					tempC = rand.nextInt(30);
				}
				dCards.add(Cards.get(tempC));
				Cards.get(tempC).setUsed();
				if(p_stay == false) {
					dealer_turn=false;
					
				}
				if(tempMax>=20) {
					hit_stay_q= false;
					play_more_q = true;
					d_stay = true;
					setWinner();
				}
				
			}
		}
		
	}
	
	public class ActStay implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true && dealer_turn == false) {
			//	System.out.println("You clicked 'STAY'");
				
					int tempMax = 0;
					if (pMaxTotal <= 20) {
						tempMax = pMaxTotal;
					} else {
						tempMax = pMinTotal;
					}
					String mess = ("Player 1 decided to stay! (total: " + Integer.toString(tempMax) + ")");
					Log.add(new Message(mess, "Player1"));
					
				if(p_stay == false) {
					p_stay = true;
				
					if (d_stay == true) {
						refresher();
						setWinner();
						hit_stay_q = false;
						play_more_q = true;
						
					}
					else {
						dealer_turn = true;
					}
				}
				else {
					dealer_turn = true;
					
				}
			}
			else if (hit_stay_q == true && dealer_turn == true) {
					
				
					
					int tempMax = 0;
					if (dMaxTotal <= 20) {
						tempMax = dMaxTotal;
					} else {
						tempMax = dMinTotal;
					}
					String mess = ("Player 2 decided to stay! (total: " + Integer.toString(tempMax) + ")");
					Log.add(new Message(mess, "Player2"));
					
				if(d_stay == false) {
					d_stay = true;
					dealer_turn = false;
					
					if(p_stay == true) {
						refresher();
						setWinner();
						hit_stay_q = false;
						dealer_turn = false;
						play_more_q = true;
						
					}
					
				}else {
					dealer_turn = false;
					
				}
				
			}
		}
		
	}
	
	public class ActYes implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		//	System.out.println("You clicked 'YES'");
			
			for (Card c : Cards) {
				c.setNotUsed();
			}
			
			pCards.clear();
			dCards.clear();
			pDeck.clear();
			dDeck.clear();
			Log.clear();
			
			play_more_q = false;
			hit_stay_q = true;
			
			tempC = rand.nextInt(30);
			pCards.add(Cards.get(tempC));
			Cards.get(tempC).setUsed();
	//		System.out.println("Card " + pCards.get(0).name + " of " + pCards.get(0).shape + " added to the player's cards.");
			
			tempC = rand.nextInt(30);
			while (Cards.get(tempC).used == true) {
				tempC = rand.nextInt(30);
			}
			dCards.add(Cards.get(tempC));
			Cards.get(tempC).setUsed();
	//		System.out.println("Card " + dCards.get(0).name + " of " + dCards.get(0).shape + " added to the dealer's cards.");
			
			tempC = rand.nextInt(30);
			while (Cards.get(tempC).used == true) {
				tempC = rand.nextInt(30);
			}
			pCards.add(Cards.get(tempC));
			Cards.get(tempC).setUsed();
	//		System.out.println("Card " + pCards.get(1).name + " of " + pCards.get(1).shape + " added to the player's cards.");
			
			tempC = rand.nextInt(30);
			while (Cards.get(tempC).used == true) {
				tempC = rand.nextInt(30);
			}
			dCards.add(Cards.get(tempC));
			Cards.get(tempC).setUsed();
		//	System.out.println("Card " + dCards.get(1).name + " of " + dCards.get(1).shape + " added to the dealer's cards.");
			
			tempC = rand.nextInt(40);
			for(int i=0; i<4; i++) {
				while (Cards.get(tempC).used == true) {
					tempC = rand.nextInt(40);
				}
				pDeck.add(Cards.get(tempC));
				Cards.get(tempC).setUsed();
			}
			
			tempC = rand.nextInt(40);
			for(int i=0; i<4; i++) {
				while (Cards.get(tempC).used == true) {
					tempC = rand.nextInt(40);
				}
				dDeck.add(Cards.get(tempC));
				Cards.get(tempC).setUsed();
			}
				 pc1 = false;
				 pc2 = false;
				 pc3 = false;
				 pc4 = false;
				 dc1 = false;
				 dc2 = false;
				 dc3 = false;
				 dc4 = false;
		}
		
	}
	
	public class ActNo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		//	System.out.println("You clicked 'NO'");
			Main.terminator = true;
			dispose();
		}
		
	}
	
	public class ActMinus implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	public class ActMinus1 extends ActMinus implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true) {
			
				if (dealer_turn == false && pc1 == false) {
					pCards.add(new Card(-pDeck.get(0).getValue(), pDeck.get(0).getShape(), 41));
					if(d_stay == false) {
						dealer_turn = true;
					}
					pc1 = true;
			} else if(dealer_turn == true && dc1 == false){
					dCards.add(new Card(- dDeck.get(0).getValue(), dDeck.get(0).getShape(), 41));
					if(p_stay == false) {
						dealer_turn = false;
					}
					dc1 = true;
			}	
			}
		}
	}
	
	public class ActMinus2 extends ActMinus implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true) {
				if (dealer_turn == false && pc2 == false) {
					pCards.add(new Card(-pDeck.get(1).getValue(), pDeck.get(1).getShape(), 41));
					if(d_stay == false) {
						dealer_turn = true;
					}
				pc2 = true;
			} else if(dealer_turn == true && dc2 == false){
				dCards.add(new Card(- dDeck.get(1).getValue(), dDeck.get(1).getShape(), 41));
				if(p_stay == false) {
					dealer_turn = false;
				}
				dc2 = true;
			}	
			}
		}
	}
	
	public class ActMinus3 extends ActMinus implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true) {
				if (dealer_turn == false && pc3 == false) {
					pCards.add(new Card(-pDeck.get(2).getValue(), pDeck.get(2).getShape(), 41));
					if(d_stay == false) {
						dealer_turn = true;
					}
				pc3 = true;
				} else if(dealer_turn == true && dc3 == false){
					dCards.add(new Card(- dDeck.get(2).getValue(), dDeck.get(2).getShape(), 41));
					if(p_stay == false) {
						dealer_turn = false;
					}
					dc3 = true;
			}

			}
		}
	}
	
	public class ActMinus4 extends ActMinus implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true) {
				if (dealer_turn == false && pc4 == false) {
					pCards.add(new Card(-pDeck.get(3).getValue(), pDeck.get(3).getShape(), 41));
					if(d_stay == false) {
						dealer_turn = true;
					}
				pc4 = true;
			} else if(dealer_turn == true && dc4 == false){
				dCards.add(new Card(- dDeck.get(3).getValue(), dDeck.get(3).getShape(), 41));
				if(p_stay == false) {
					dealer_turn = false;
				}
				dc4 = true;
			}	
			}
		}
	}
	
	public class ActPlus implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	public class ActPlus1 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true) {
				if (dealer_turn == false && pc1 == false) {
					pCards.add(pDeck.get(0));
					if(d_stay == false) {
						dealer_turn = true;
					}
				pc1 = true;
			} else if(dealer_turn == true && dc1 == false){
				dCards.add(dDeck.get(0));
				if(p_stay == false) {
					dealer_turn = false;
				}
				dc1 = true;
			}	
			}
		}
	}
	
	public class ActPlus2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true) {
				if (dealer_turn == false && pc2 == false) {
					pCards.add(pDeck.get(1));
					if(d_stay == false) {
						dealer_turn = true;
					}
				pc2 = true;
			} else if(dealer_turn == true && dc2 == false){
				dCards.add(dDeck.get(1));
				if(p_stay == false) {
					dealer_turn = false;
				}
				dc2 = true;
			}	
			}
		}
	}
	
	public class ActPlus3 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true) {
				if (dealer_turn == false && pc3 == false) {
					pCards.add(pDeck.get(2));
					if(d_stay == false) {
						dealer_turn = true;
					}
				pc3 = true;
			} else if(dealer_turn == true && dc3 == false){
				dCards.add(dDeck.get(2));
				if(p_stay == false) {
					dealer_turn = false;
				}
				dc3 = true;
			}	
			}
		}
	}
	
	public class ActPlus4 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hit_stay_q == true) {
				if (dealer_turn == false && pc4 == false) {
					pCards.add(pDeck.get(3));
					if(d_stay == false) {
						dealer_turn = true;
					}
				pc4 = true;
			} else if(dealer_turn == true && dc4 == false){
				dCards.add(dDeck.get(3));
				if(p_stay == false) {
					dealer_turn = false;
				}
				dc4 = true;
			}	
			}
		}
	}
	
	
}