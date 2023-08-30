package com.learning.hello.contoller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import com.learning.hello.contoller.exception.UnsupportedActionException;
import com.learning.hello.model.game2584.Game2584;

import jakarta.servlet.http.HttpServletResponse;
import com.learning.hello.model.TennisGame.Tennis;
import com.learning.hello.model.TennisGame.TennisDBConnect;
import com.learning.hello.model.TennisGame.Players;

public class TennisController implements IController {
	String player1="player1";
	String player2 ="player2";
	Tennis tennis;
	Players p1;
	Players p2;
	boolean start = false;
	boolean gameWon = false;
	boolean oldMatches = false;
	int i=0;
	int rowIndex=0;
	boolean servep1, servep2;
	String recap, last, previous, next, oldP1, oldP2;
	TennisDBConnect db = new TennisDBConnect();
	public ArrayList<String> prevMatches= new ArrayList<>();
	public ArrayList<String> players= new ArrayList<>();
	
	
	@Override
	public void processGet(IWebExchange webExchange, TemplateEngine templateEngine, HttpServletResponse resp)
			throws UnsupportedActionException, IOException {
		// TODO Auto-generated method stub
		final WebContext ctx = new WebContext(webExchange);
		//String [] players = {"ankan","ankush","bibhu","Gaurav","chirag"};
		players = db.getAllPlayerNames();
		prevMatches = db.loadPrevMatches();
		
//		int i=0;
//		boolean gameWon = false;
//		var request = webExchange.getRequest();
		ctx.setVariable("p1Name", player1);
		ctx.setVariable("p2Name", player2);
		ctx.setVariable("players", players);
		ctx.setVariable("prevMatches", prevMatches);
		System.out.println(prevMatches);
		if(start) {
			
			
			ctx.setVariable("p1Score", p1.getScore());
			ctx.setVariable("p1Game", p1.getGamesWon());
			ctx.setVariable("p1Set", p1.getSetsWon());
			
			ctx.setVariable("p2Score", p2.getScore());
			ctx.setVariable("p2Game", p2.getGamesWon());
			ctx.setVariable("p2Set", p2.getSetsWon());
			if(gameWon) {
				ctx.setVariable("winner", p1.getSetsWon() >= 2 ?p1.getName() : p2.getSetsWon() >= 2 ? p2.getName() : "");
			}
		}
		if(!start && (recap!= null || oldMatches == true)) {
			ctx.setVariable("p1Score", tennis.currList.get(0).get(0));
			ctx.setVariable("p1Game", tennis.currList.get(0).get(1));
			ctx.setVariable("p1Set",tennis.currList.get(0).get(2));
			
			ctx.setVariable("p2Score", tennis.currList.get(0).get(3));
			ctx.setVariable("p2Game", tennis.currList.get(0).get(4));
			ctx.setVariable("p2Set", tennis.currList.get(0).get(5));
			recap = null;
		}
		
		if(!start && (previous!= null || next != null)) {
			if(previous != null && (rowIndex > 0))
				rowIndex -=1;
			if(next != null && (tennis.currList.size()-2 > rowIndex))
				rowIndex +=1;
			ctx.setVariable("p1Score", tennis.currList.get(rowIndex).get(0));
			ctx.setVariable("p1Game", tennis.currList.get(rowIndex).get(1));
			ctx.setVariable("p1Set",tennis.currList.get(rowIndex).get(2));
			
			ctx.setVariable("p2Score", tennis.currList.get(rowIndex).get(3));
			ctx.setVariable("p2Game", tennis.currList.get(rowIndex).get(4));
			ctx.setVariable("p2Set", tennis.currList.get(rowIndex).get(5));
			previous = null;
			next = null;
		}
//		if(tennis.server == 1) {
//			servep1 = true;
//			servep2 = false;
//		}
//		else {
//			servep1 = false;
//			servep2 = true;
//		}
		
		
		templateEngine.process("tennis", ctx, resp.getWriter());
		
	}

	@Override
	public void processPost(IWebExchange webExchange, TemplateEngine templateEngine, HttpServletResponse resp)
			throws UnsupportedActionException, IOException {
		// TODO Auto-generated method stub
		var request = webExchange.getRequest();
		String submitBtn = request.getParameterValue("players");
		if(submitBtn!= null && start == false) {
//			tennis.currList.clear();
			player1 = request.getParameterValue("player1");
			player2 = request.getParameterValue("player2");
			p1 = new Players(player1);
			p2 = new Players(player2);
			tennis = new Tennis(p1,p2);
			start = true;
			submitBtn = null;
			servep1 = true;
		}
		
		
		String p1Won = request.getParameterValue("p1Won");
		String p2Won = request.getParameterValue("p2Won");
		System.out.println(tennis);
		while(!gameWon && start == true && (p1Won!= null || p2Won!= null)) {
			System.out.println("Enter player name who won this service-");
			
			//System.out.println(playerWon);
			if(p1Won!= null )
				gameWon = tennis.updateScore(player1);
			if(p2Won!= null )
				gameWon = tennis.updateScore(player2);
			tennis.updateScoreBoard();
			tennis.updateCurrentList();
			System.out.println(tennis);
			//System.out.println(gameWon + " round - " + i );
			p1Won = null;
			p2Won = null;
			i+=1;
			rowIndex +=1;
		}
		recap = request.getParameterValue("initial");
		previous = request.getParameterValue("previous");
		next = request.getParameterValue("next");
		

		if((recap != null) || (next != null) || (previous != null)) {
			start = false;
			System.out.println("inside recap");
		}
		last = request.getParameterValue("last");
		if(last != null && last.equals("last")) {
			start = true;
			recap = null;
			oldMatches = false;
			System.out.println("inside last");
		}

		String selectedMatchIndexStr = request.getParameterValue("selectedMatchIndex");
        
        if (selectedMatchIndexStr != null) {
            try {
                int selectedMatchIndex = Integer.parseInt(selectedMatchIndexStr);
              System.out.println("index- "+selectedMatchIndex);
              int matchId = Integer.parseInt(prevMatches.get(selectedMatchIndex).substring(0,2));
                System.out.println("match id - " + matchId);

                tennis.loadOldMatches(matchId);
                System.out.println(selectedMatchIndex);
            } catch (NumberFormatException e) {
               
            }
            start = false;
            oldMatches = true;
        }
		
		resp.sendRedirect(request.getRequestPath());
		
	}

}
