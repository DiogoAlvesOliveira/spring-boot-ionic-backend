package com.diogodga.diogodga.config;
import java.text.ParseException;

import com.diogodga.diogodga.services.EmailService;
import com.diogodga.diogodga.services.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public boolean instantiateDataBase() throws ParseException {
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}