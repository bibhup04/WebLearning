package com.learning.hello;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.contoller.Players;
import com.learning.hello.contoller.MangathaGame;
import com.learning.hello.contoller.Card;

@WebServlet("/manga")
public class MangathaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	  private Players p1;
	  private Players p2;
	  private MangathaGame mangathaGame;
	  
	  private JakartaServletWebApplication application;
	  private TemplateEngine templateEngine;
	  
	  boolean gameState = false;
	  int gameRound = 1;
	  
	  @Override
	  public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    
	    application = JakartaServletWebApplication.buildApplication(getServletContext());
	    final WebApplicationTemplateResolver templateResolver = 
	        new WebApplicationTemplateResolver(application);
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    templateResolver.setPrefix("/WEB-INF/templates/");
	    templateResolver.setSuffix(".html");
	    templateEngine = new TemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	  }
	  
	  @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  final IWebExchange webExchange = 
			        this.application.buildExchange(req, resp);
		  final WebContext ctx = new WebContext(webExchange);

		  String btnId = req.getParameter("btn");
		  //System.out.println(btnId);
		  if(btnId != null) {
			  if(btnId.equals("p1")) {
			  
				  String name1 = req.getParameter("Player1Name");
				  String bet1 = req.getParameter("Player1BetAmount");
				  //System.out.println(req.getParameter("Player1CardPosition"));
				  Card cardType1 = new Card(req.getParameter("Player1Card"));
				  int cardPosition1 = req.getParameter("Player1CardPosition").equals("in") ? 0 : 1;
				  p1 = new Players(name1, Integer.parseInt(bet1), cardType1, cardPosition1);
//				  System.out.println("name -"+p1.getName());
//				  System.out.println("bet -"+p1.getBet());
//				  System.out.println("type -"+p1.getCardPosition());
//				  System.out.println("card -"+p1.getCardType());

			  }
			  if(btnId.equals("p2")) {
				  
				  String name2 = req.getParameter("Player2Name");
				  String bet2 = req.getParameter("Player2BetAmount");
				  Card cardType2 = new Card(req.getParameter("Player2Card"));
				  int cardPosition2 = req.getParameter("Player2CardPosition").equals("in") ? 0 : 1;
				  p2 = new Players(name2, Integer.parseInt(bet2), cardType2, cardPosition2);
//				  System.out.println("name -"+p2.getName());
//				  System.out.println("bet -"+p2.getBet());
//				  System.out.println("type -"+p2.getCardPosition());
//				  System.out.println("card -"+p2.getCardType());
				  
			  }
			  
			  mangathaGame = new MangathaGame(p1, p2);
		  }
		  String dealer = req.getParameter("Dealer");
		  if(dealer != null) {
			  if(gameRound >= 53) {
				  ctx.setVariable("winner", "Winner is Dealer");
				  ctx.setVariable("WiningAmount", "Wining Amount Rs. " + (p1.getBet() + p2.getBet()));
				  var out = resp.getWriter();
				    
				templateEngine.process("mangatha", ctx, out);
				return;

			  }
			  if(p1 == null || p2 == null) {
				  ctx.setVariable("noPlayer", "Please enter player details");
				  var out = resp.getWriter();
				    
					templateEngine.process("mangatha", ctx, out);
					return;
				  
			  }
			  gameState = mangathaGame.gameOver();
			  ctx.setVariable("reading", mangathaGame.getTopCard());
			  ctx.setVariable("round", gameRound);
			  //p1 details
			  ctx.setVariable("p1Name", p1.getName());
			  ctx.setVariable("p1BetAmmount", p1.getBet());
			  ctx.setVariable("p1CardType", p1.getCardType());
			  ctx.setVariable("p1CardPosition", p1.getCardPosition());
			  
			  //p2 details
			  ctx.setVariable("p2Name", p2.getName());
			  ctx.setVariable("p2BetAmmount", p2.getBet());
			  ctx.setVariable("p2CardType", p2.getCardType());
			  ctx.setVariable("p2CardPosition", p2.getCardPosition());
			  
			  gameRound +=1;
		  }
		  
		  if(gameState) {
			  if(p1.getWin()) {
				  ctx.setVariable("winner", "Winner is " + p1.getName());
			  }
			  if(p2.getWin()) {
					  ctx.setVariable("winner", "Winner is " + p2.getName());
			  }
			  ctx.setVariable("WiningAmount", "Wining Amount Rs. " + (p1.getBet() + p2.getBet()));
		  }
		var out = resp.getWriter();
			    
		templateEngine.process("mangatha", ctx, out);

		 // doGet(req, resp);
		  }
		  




	  
	  @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    final IWebExchange webExchange = this.application.buildExchange(req, resp);
	    final WebContext ctx = new WebContext(webExchange);
	    //ctx.setVariable("reading", oc.getReading());
	    templateEngine.process("mangatha", ctx, resp.getWriter());
	    
	   
	    //System.out.println(oc.getReading());
	  }

}
