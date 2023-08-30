package com.learning.hello.contoller;

public class MangathaGame {
	Players p1;
	Players p2;
	Deck deck;
	Card topCard;
	int index;
//	Card p1Card;
//	Card p2Card;
	public MangathaGame(Players p1, Players p2) {
		this.p1 = p1;
		this.p2 = p2;
		deck = new Deck();
		deck.shuffle();
		index = 0;
	}
	
	public boolean gameOver() {
		topCard = deck.removeFromTop();
		if(topCard.equals(p1.getCardType()) && (index % 2 == p1.getCardPosition())) {
			p1.setWin(true);
			return true;
		}
		else if(topCard.equals(p2.getCardType()) && (index % 2 == p2.getCardPosition())) {
			p2.setWin(true);
			return true;
		}
		index += 1;
		return false;
	}
	
	public Card getTopCard() {
		return topCard;
	}
	

}
