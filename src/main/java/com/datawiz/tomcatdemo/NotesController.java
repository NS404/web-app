package com.datawiz.tomcatdemo;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "NotesController", value = "/NotesController")
public class NotesController extends HttpServlet {


    private JakartaServletWebApplication application;
    private ITemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        this.application = JakartaServletWebApplication.buildApplication(getServletContext());
        this.templateEngine = buildTemplateEngine(this.application);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Object> notes = Collections.singletonList(request.getParameter("notes"));


    }

    private static ITemplateEngine buildTemplateEngine(final IWebApplication application){

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
}
