package com.it.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.io.Writer;

public class ThymeleafUtil {
    private static final TemplateEngine engine;
    private static ServletContext servletContext;

    static {
        engine = new TemplateEngine();
        FileTemplateResolver r = new FileTemplateResolver();
        r.setCharacterEncoding("UTF-8");
        engine.setTemplateResolver(r);
        r.setPrefix("src/main/webapp/pages/");
    }


    public static void process(String template, IContext context, Writer writer) {
        engine.process(template, context, writer);//启动引擎
    }
}