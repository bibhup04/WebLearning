package com.learning.hello.contoller;

public class Players {
	String name;
	int bet;
	Card cardType;
	int cardPosition;
	boolean win = false;
	
	public Players(String name, int bet, Card cardType, int cardPosition) {
		this.name = name;
		this.bet = bet;
		this.cardType = cardType;
		this.cardPosition = cardPosition;
	}
	
	public void setWin(boolean win) {
		 this.win = win;
	}
	
	public String getName() {
		return name;
	}
	public int getBet() {
		return bet;
	}
	public Card getCardType() {
		return cardType;
	}
	public int getCardPosition() {
		return cardPosition;
	}
	
	public boolean getWin() {
		return win;
	}

}
