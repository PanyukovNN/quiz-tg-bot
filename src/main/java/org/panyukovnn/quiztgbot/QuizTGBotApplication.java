package org.panyukovnn.quiztgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "org.panyukovnn.quiztgbot.repository")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"org.panyukovnn.quiztgbot"})
public class QuizTGBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizTGBotApplication.class);
    }
}
