package com.learning.hello.model.TennisGame;

public class Sets {
	Players p1;
	Players p2;
	
	public Sets(Players p1, Players p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public boolean updateSets() {
		if(p1.getGamesWon() > p2.getGamesWon()) {
			p1.setSetsWon(p1.getSetsWon()+1);
		}
		else {
			p2.setSetsWon(p2.getSetsWon()+1);
		}
		p1.setGamesWon(0);
		p2.setGamesWon(0);
		
		if(p1.getSetsWon() == 2 || p2.getSetsWon() == 2) {
			
			return true;
		}
		return false;
		
	}
}
