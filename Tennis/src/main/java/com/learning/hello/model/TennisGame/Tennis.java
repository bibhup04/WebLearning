package com.learning.hello.model.TennisGame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tennis {
	Players p1;
	Players p2;
	Games game; 
	String player1, player2, serviceWinner;
	public int server;
	int player1Id, player2Id, matchId;
	TennisDBConnect db;
	public ArrayList<ArrayList<String>> currList= new ArrayList<>();
	public ArrayList<String> prevMatches= new ArrayList<>();
	int rowIndex = 0;
	int colIndex = 0;
	
	public Tennis() {
		db = new TennisDBConnect();
	}
	
	public Tennis(Players p1, Players p2) {
//		p1 = new Players(name1);
//		p2 = new Players(name2);
		this.p1 = p1;
		this.p2 = p2;
		player1 = p1.getName();
		player2 = p2.getName();
		server = 1;
		game = new Games(p1,p2);
		db = new TennisDBConnect();
	
		
		try {
			checkDb();
			loadPrevMatches();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadPrevMatches() {
		prevMatches = db.loadPrevMatches();
	}
	
	
	public void checkDb() throws SQLException {
		player1Id = db.getPlayerId(player1);
		player2Id = db.getPlayerId(player2);
		
		if(player1Id == -1 || player2Id == -1) {
			if(player1Id == -1)
				db.insertData("INSERT INTO Players (playerName) VALUES ('" + player1 + "');");
			if(player2Id == -1)
				db.insertData("INSERT INTO Players (playerName) VALUES('" + player2 + "');");
			db.insertData("INSERT INTO Matches (player1,player2,winnerId) VALUES((SELECT playerId FROM Players WHERE playerName = '"+player1+"'),(SELECT playerId FROM Players WHERE playerName = '"+player2+"'), '-1');");
			player1Id = db.getPlayerId(player1);
			player2Id = db.getPlayerId(player2);
			matchId = db.getMatchId(player1Id, player2Id);
		}
		else {
			matchId = db.getMatchId(player1Id, player2Id);
			if(matchId == -1) {
				db.insertData("INSERT INTO Matches (player1,player2,winnerId) VALUES((SELECT playerId FROM Players WHERE playerName = '"+player1+"'),(SELECT playerId FROM Players WHERE playerName = '"+player2+"'), '-1');");
				matchId = db.getMatchId(player1Id, player2Id);
			}
				
			if(db.getGameState(matchId) != -1) {
				db.insertData("INSERT INTO Matches (player1,player2,winnerId) VALUES((SELECT playerId FROM Players WHERE playerName = '"+player1+"'),(SELECT playerId FROM Players WHERE playerName = '"+player2+"'), '-1');");
				matchId = db.getMatchId(player1Id, player2Id);
			}
			else {
				ResultSet rs = db.loadGame(matchId);
				if(rs.next()) {
					System.out.println(player1 +"-"+ rs.getString("p1Score") + ", game- "+ rs.getString("p1Score")+ ", sets- " + rs.getInt("p1SetWon") );
					
					System.out.println(player2 +"-"+ rs.getString("p2Score") + ", game- "+ rs.getString("p2Score")+ ", sets- " + rs.getInt("p2SetWon") );
					p1.setServesWon(rs.getString("p1Score"));
					p1.setGamesWon(rs.getInt("p1GameWon"));
					p1.setSetsWon(rs.getInt("p1SetWon"));
					
					p2.setServesWon(rs.getString("p2Score"));
					p2.setGamesWon(rs.getInt("p2GameWon"));
					p2.setSetsWon(rs.getInt("p2SetWon"));
					
					System.out.println(rs.getInt("serverId")+" and player id "+ player1Id);
					System.out.println(rs.getInt("serverId") == player1Id);
					
					server = rs.getInt("serverId") == player1Id ? 1 : 2;
				}
			}
		}
	}
	
	public boolean updateScore(String name) {
		serviceWinner = name;
		
		if(name.equals(player1)) {
			int serves = p1.getServesWon();
			
			if(serves < 5) {
				p1.setServesWon(serves+1);
			}
		}
		else {
			int serves = p2.getServesWon();
			
			if(serves < 5) {
				p2.setServesWon(serves+1);
			}
		}
		
		if(compareScores()) {
			if(server == 1)
				server =2;
			else 
				server =1;
			return game.updateGame();
//			gameWon = false;
		}
		return false;
	}
	
	public boolean compareScores() {
		if(p1.getServesWon() == 3 && p2.getServesWon() == 3) {
			return false;
		}
		if(p1.getServesWon() == 4 && p2.getServesWon() == 4) {
			p1.setServesWon(3);
			p2.setServesWon(3);
			return false;
		}
		if(p1.getServesWon() > 3 &&  p1.getServesWon() - p2.getServesWon() >= 2) {
			p1.setServesWon(6);
			
			return true;
		}
		if(p2.getServesWon() > 3 &&  p2.getServesWon() - p1.getServesWon() >= 2) {
			p2.setServesWon(6);
			return true;
		}
		
		return false;
	}
	
	public void updateScoreBoard() {
		db.insertIntoScoreBoard(matchId, server == 1? player1Id: player2Id,
				player1Id, p1.getScore() , p1.getGamesWon(), p1.getSetsWon(),
				player2Id, p2.getScore(), p2.getGamesWon(), p2.getSetsWon(),"Nice" );
			try {
				if(p1.getSetsWon() >= 2)
					db.insertData("UPDATE Matches SET winnerId = '" + player1Id + "' WHERE matchId = '" + matchId + "';");
				if(p2.getSetsWon() >= 2)
					db.insertData("UPDATE Matches SET winnerId = '" + player2Id + "' WHERE matchId = '" + matchId + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			
	}
	
	public void loadOldMatches(int id) {
		ArrayList<String> row = new ArrayList<>();
		currList.clear();
		System.out.println("tennis  old matches");
		try {
			ResultSet rs = db.loadOldMatches(id);
			while(rs.next()) {
				row.add(rs.getString("p1Score"));
				row.add(String.valueOf(rs.getInt("p1GameWon")));
				row.add(String.valueOf(rs.getInt("p1SetWon")));
				
				row.add(rs.getString("p1Score"));
				row.add(String.valueOf(rs.getInt("p2GameWon")));
				row.add(String.valueOf(rs.getInt("p2SetWon")));
				
				currList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("load old matches");
		System.out.println(currList);
	}
	
	public void updateCurrentList() {
		ArrayList<String> row = new ArrayList<>();
		row.add(p1.getScore());
		row.add(String.valueOf(p1.getGamesWon()));
		row.add(String.valueOf(p1.getSetsWon()));
		
		row.add(p2.getScore());
		row.add(String.valueOf(p2.getGamesWon()));
		row.add(String.valueOf(p2.getSetsWon()));
		
		currList.add(row);
		
		System.out.println(currList);
	}
	
	
	@Override
	public String toString() {
//		updateScoreBoard();
//		updateCurrentList();
		String result="Player Name     Score  Game Won  Set Won \n";

		result += "    "+ p1.getName() +(server == 1?"*":"");
		result +="\t\t" + p1.getScore() + " \t  " + p1.getGamesWon()+"    \t   "+ p1.getSetsWon() +"  \n";

		result += "    "+ p2.getName() +(server == 2?"*":"");
		result +="\t\t" + p2.getScore() + " \t  " + p2.getGamesWon()+"    \t   "+ p2.getSetsWon() +"  \n";
		result +=  p1.getSetsWon() >= 2 ? " Winner is " + p1.getName() : p2.getSetsWon() >= 2 ? " Winner is " + p2.getName() : "";
		return result;
	} 
}
