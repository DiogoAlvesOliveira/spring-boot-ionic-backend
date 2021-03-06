package com.diogodga.diogodga.config;

import com.diogodga.diogodga.services.DBService;
import com.diogodga.diogodga.services.EmailService;
import com.diogodga.diogodga.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase(DBService dbService) throws ParseException {

        if (!"create".equals(strategy)){
            return false;
        }
        dbService.instantiateTestDatabaase();
        return true;
    }
    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }

}
