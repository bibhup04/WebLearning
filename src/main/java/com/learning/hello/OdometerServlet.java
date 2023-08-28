package com.learning.hello;

import jakarta.servlet.ServletConfig; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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

import com.learning.hello.contoller.OdometerController;

@WebServlet("/odom")
public class OdometerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private OdometerController oc;
	  private JakartaServletWebApplication application;
	  private TemplateEngine templateEngine;
	  @Override
	  public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    oc = new OdometerController(4);
	    application = JakartaServletWebApplication.buildApplication(getServletContext());
	    final WebApplicationTemplateResolver templateResolver = 
	        new WebApplicationTemplateResolver(application);
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    templateResolver.setPrefix("/WEB-INF/odometerTemplates/");
	    templateResolver.setSuffix(".html");
	    templateEngine = new TemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	  }
	  
	  @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		  String odometerSize = req.getParameter("odometerSize");
		  if(odometerSize !=null) {
			  oc.changeSize(Integer.parseInt(odometerSize));
		  }
		  String buttonId = req.getParameter("buttonId");
		  if (buttonId != null) {
			  oc.incrementDecrement(buttonId);

		  }
		  
		  Cookie readingCookie = new Cookie("odometerReading", Integer.toString(oc.getReading()));
	        resp.addCookie(readingCookie);
		  doGet(req, resp);



	  }
	  
	  @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    final IWebExchange webExchange = this.application.buildExchange(req, resp);
	    final WebContext ctx = new WebContext(webExchange);
	    ctx.setVariable("reading", oc.getReading());
	    templateEngine.process("odom", ctx, resp.getWriter());
	    String odometerReading = getCookieValue(req, "odometerReading");
        ctx.setVariable("reading", odometerReading);
	   
	    System.out.println(oc.getReading());
	  }
	  
	  private String getCookieValue(HttpServletRequest request, String cookieName) {
	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals(cookieName)) {
	                    return cookie.getValue();
	                }
	            }
	        }
	        return null;
	    }
}
