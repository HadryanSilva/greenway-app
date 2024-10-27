package br.com.fiap.app.stepdefs;

import br.com.fiap.app.GreenwayAppApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
classes = GreenwayAppApplication.class)
public class StepDefsDefault {

    @Bean
    @ConditionalOnMissingBean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

}
