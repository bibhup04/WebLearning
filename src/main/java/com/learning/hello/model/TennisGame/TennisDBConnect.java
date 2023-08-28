package com.learning.hello.model.TennisGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TennisDBConnect {
	private PreparedStatement insert = null;
	private Connection cnx = null;
	private ResultSet rs;
	public ArrayList<ArrayList<String>> prevMatches = new ArrayList<>();

	public TennisDBConnect() {
		try {
			this.cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/TennisDB", "bibhu04", "bibhu04");
			System.out.println("connected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertData(String updateQuery) throws SQLException {
		PreparedStatement show = cnx.prepareStatement(updateQuery);
		show.executeUpdate();
		System.out.println("data added");
	}

//	public boolean isPlayerPresent(String playerName) {
//	    try {
//	        
//	    	show = cnx.prepareStatement("SELECT playerId FROM Players WHERE playerName ='"+playerName+"';");
//	        PreparedStatement preparedStatement = connection.prepareStatement(query);
//	        preparedStatement.setString(1, playerName);
//
//	        ResultSet resultSet = preparedStatement.executeQuery();
//	        
//	        boolean playerExists = resultSet.next();
//	        
//	        // Close resources (connection, statement, result set) when done
//	        
//	        return playerExists;
//	        
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	        // Handle the exception appropriately
//	        return false; // Return false if an exception occurs
//	    }
//	}

	public int getPlayerId(String playerName) {
		PreparedStatement show;
		try {
			show = cnx.prepareStatement("SELECT playerId FROM Players WHERE playerName = '" + playerName + "';");
			rs = show.executeQuery();
			if (rs.next())
				return rs.getInt("playerId");

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return -1;
	}

	public int getMatchId(int player1Id, int player2Id) {
		PreparedStatement show;
		try {
			show = cnx.prepareStatement("SELECT matchId FROM Matches WHERE player1 = '" + player1Id
					+ "' AND player2 = '" + player2Id + "'ORDER BY matchId DESC LIMIT 1;");
			rs = show.executeQuery();
			if (rs.next())
				return rs.getInt("matchId");

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return -1;

	}

	public int getGameState(int matchId) {
		PreparedStatement show;
		try {
			show = cnx.prepareStatement(
					"SELECT winnerId FROM Matches WHERE matchId = '" + matchId + "'ORDER BY matchId DESC LIMIT 1;");
			rs = show.executeQuery();
			if (rs.next())
				return rs.getInt("winnerId");

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return -1;

	}

	public void insertIntoScoreBoard(int matchId, int serverId, int player1Id, String p1Score, int p1GamesWon,
			int p1SetsWon, int player2Id, String p2Score, int p2GamesWon, int p2SetsWon, String commentary) {

		try {

			String insertQuery = "INSERT INTO ScoreBoard "
					+ "(matchId, serverId, p1, p1Score, p1GameWon, p1SetWon, p2, p2Score, p2GameWon, p2SetWon, commentary) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStatement = cnx.prepareStatement(insertQuery);
			preparedStatement.setInt(1, matchId);
			preparedStatement.setInt(2, serverId);
			preparedStatement.setInt(3, player1Id);
			preparedStatement.setString(4, p1Score);
			preparedStatement.setInt(5, p1GamesWon);
			preparedStatement.setInt(6, p1SetsWon);
			preparedStatement.setInt(7, player2Id);
			preparedStatement.setString(8, p2Score);
			preparedStatement.setInt(9, p2GamesWon);
			preparedStatement.setInt(10, p2SetsWon);
			preparedStatement.setString(11, commentary);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet loadGame(int matchId) throws SQLException {
		PreparedStatement show;
			show = cnx.prepareStatement("SELECT * FROM ScoreBoard WHERE matchId = '"+matchId +"'ORDER BY serviceId DESC LIMIT 1;");
			rs = show.executeQuery();
//			if (rs.next()) 
//				return rs;
			return rs;

	}

	public ArrayList<String> loadPrevMatches() {
	    ArrayList<String> matchDetails = new ArrayList<>();
	    PreparedStatement show;

	    try {
	    	show = cnx.prepareStatement(
	    		    "SELECT M.matchId, P1.playerName AS player1Name, P2.playerName AS player2Name, " +
	    		    "CASE WHEN M.winnerId = -1 THEN NULL ELSE W.playerName END AS winnerName " +
	    		    "FROM Matches M " +
	    		    "INNER JOIN Players P1 ON M.player1 = P1.playerId " +
	    		    "INNER JOIN Players P2 ON M.player2 = P2.playerId " +
	    		    "LEFT JOIN Players W ON M.winnerId = W.playerId " +
	    		    "ORDER BY M.matchId DESC;");

	        ResultSet rs = show.executeQuery();

	        while (rs.next()) {
	            String matchInfo = rs.getInt("matchId") + ", " +
	                               rs.getString("player1Name") + ", " +
	                               rs.getString("player2Name") + ", " +
	                               rs.getString("winnerName");
	            matchDetails.add(matchInfo);
	        }
	    } catch (SQLException e) {
	        // Handle the exception
	        e.printStackTrace();
	    }

	    return matchDetails;
	}
	
	public ArrayList<String> getAllPlayerNames() {
        ArrayList<String> playerNamesList = new ArrayList<>();
        PreparedStatement show;
        try {
        	show = cnx.prepareStatement("SELECT playerName FROM Players");
        	ResultSet rs = show.executeQuery();
            
            while (rs.next()) {
                String playerName = rs.getString("playerName");
                playerNamesList.add(playerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly
        }

        return playerNamesList;
    }



}
