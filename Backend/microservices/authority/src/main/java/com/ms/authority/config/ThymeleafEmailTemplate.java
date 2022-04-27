package com.ms.authority.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.nio.charset.StandardCharsets;

@Configuration
public class ThymeleafEmailTemplate {
    @Bean
    public SpringTemplateEngine emailTemplateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());

        return templateEngine;
    }

    public ClassLoaderTemplateResolver htmlTemplateResolver(){
        ClassLoaderTemplateResolver htmlTemplateResolver = new ClassLoaderTemplateResolver();
        htmlTemplateResolver.setPrefix("template/");
        htmlTemplateResolver.setSuffix(".html");
        htmlTemplateResolver.setTemplateMode(TemplateMode.HTML);
        htmlTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        htmlTemplateResolver.setCacheable(false);

        return htmlTemplateResolver;
    }

}
