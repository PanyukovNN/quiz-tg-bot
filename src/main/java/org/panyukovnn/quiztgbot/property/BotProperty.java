package org.panyukovnn.quiztgbot.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotProperty {

    private String BOT_NAME;
    private String BOT_TOKEN;

    @PostConstruct
    private void postConstruct() {
        System.out.println(BOT_NAME);
        System.out.println(BOT_TOKEN);
    }
}
