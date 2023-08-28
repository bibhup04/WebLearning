package com.learning.hello;

import jakarta.servlet.ServletConfig; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.contoller.Board;
import com.learning.hello.contoller.Tile;


@WebServlet("/fiboGame")
public class FibonacciGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private JakartaServletWebApplication application;
	  private TemplateEngine templateEngine;
	  Board board = new Board();
       
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
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    final IWebExchange webExchange = this.application.buildExchange(req, resp);
	    final WebContext ctx = new WebContext(webExchange);

		  
	    templateEngine.process("2584", ctx, resp.getWriter());
	  }
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    final IWebExchange webExchange = this.application.buildExchange(req, resp);
	    final WebContext ctx = new WebContext(webExchange);

	    // Retrieve the "key" parameter from the POST data
	    String keyStr = req.getParameter("key");
	    if (keyStr != null) {
	        int key = Integer.parseInt(keyStr);
	        System.out.println(key);
	        if(key == 37) {
	        	board.left();
	        	board.spawn();
	        }
			if(key == 38) {
				board.up(); //grid goes up
				board.spawn();	
			}
			if(key == 39) {
				board.right();
				board.spawn();
			}
			if(key == 40) {
				board.down();
				board.spawn();
			}
	        
	        board.print();
	       // Tile tile = new Tile();
	        Tile[] linearBoard = board.getBoard();
	        for(int i = 1 ; i < 17 ; i++) {
	        	ctx.setVariable("tile"+i,  linearBoard[i-1]);
	        	
	        }
	        templateEngine.process("2584", ctx, resp.getWriter());
	        	//System.out.println(Arrays.toString(linearBoard));
	      
	        // Process the key and generate a response based on it
	        //String response = getArrowKey(key);
	        //ctx.setVariable("response", response);
	    }

	    
	}

	
	

}
