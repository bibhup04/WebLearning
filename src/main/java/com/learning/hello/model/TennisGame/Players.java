package com.learning.hello.model.TennisGame;

import java.util.stream.IntStream;

public class Players {
	public String name;
	public int servesWon;
	public int gamesWon;
	public int setsWon;
	
	public String[] scores = {"Love", "15", "30", "40", "Adv", "Adv","Game"}; 
	
	public Players(String name) {
		this.name = name;
		this.servesWon = 0;
		this.gamesWon = 0;
		this.setsWon = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public String getScore() {
		return scores[servesWon];
	}
	
	public int getServesWon() {
		return servesWon;
	}
	public int getGamesWon() {
		return gamesWon;
	}
	
	public int getSetsWon(){
		return setsWon;
	}
	
	public void setServesWon(int score) {
		this.servesWon = score;
	}
	public void setServesWon(String score) {

		this.servesWon = IntStream.range(0, scores.length)
	            .filter(i -> scores[i].equals(score))
	            .findFirst()
	            .orElse(0);
	}
	
	
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}
	
	public void setSetsWon(int setsWon){
		this.setsWon = setsWon;
	}

}
