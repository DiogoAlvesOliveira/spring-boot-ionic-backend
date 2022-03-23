package com.diogodga.diogodga.config;

import com.diogodga.diogodga.services.DBService;
import com.diogodga.diogodga.services.EmailService;
import com.diogodga.diogodga.services.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public boolean instantiateDatabase(DBService dbService) throws ParseException {

        dbService.instantiateTestDatabaase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
