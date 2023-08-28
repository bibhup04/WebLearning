package com.learning.hello.model.TennisGame;

public class Games {
	Players p1;
	Players p2;
	Sets sets;
	private static final int MINIMUM_GAME_WON = 6; 
	
	public Games(Players p1, Players p2) {
		this.p1 = p1;
		this.p2 = p2;
		sets = new Sets(p1,p2);
	}
	
	public boolean updateGame() {
		
		if(p1.getServesWon() == 6) {
			p1.setGamesWon(p1.getGamesWon()+1);
		}
		else {
			p2.setGamesWon(p2.getGamesWon()+1);
		}
		
		p1.setServesWon(0);
		p2.setServesWon(0);
		
		if(compareGame()) {
			return sets.updateSets();
		}
		
		return false;
	}
	
	public boolean compareGame() {
		if(Math.abs(p1.getGamesWon() - p2.getGamesWon()) >= 2 ) {
			if(p1.getGamesWon() >= MINIMUM_GAME_WON || p2.getGamesWon() >= MINIMUM_GAME_WON) {
				return true;
			}
		}
		return false;
	}
}
