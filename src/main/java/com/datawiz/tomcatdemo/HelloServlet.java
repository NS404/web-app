package com.datawiz.tomcatdemo;

import java.io.*;
import java.sql.Array;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.IWebRequest;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    private ITemplateEngine templateEngine;

    private JakartaServletWebApplication application;

    public void init() {
        message = "Hello World!";

        this.application = JakartaServletWebApplication.buildApplication(getServletContext());
        this.templateEngine = buildTemplateEngine(this.application);


    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final IWebExchange webExchange = this.application.buildExchange(request, response);
        final IWebRequest webRequest = webExchange.getRequest();

        final WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        ctx.setVariable("msg",message);
        templateEngine.process("index",ctx,response.getWriter());


    }


    private ITemplateEngine buildTemplateEngine(final IWebApplication application){

        final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);


        // HTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // This will convert "index" to "/Notebook/public/index.html"
        templateResolver.setSuffix(".html");
        // Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
        templateResolver.setCacheTTLMs(3600000L);

        // Cache is set to true by default. Set to false if you want templates to
        // be automatically updated when modified.
        templateResolver.setCacheable(true);

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;

    }

    public void destroy() {
    }
}