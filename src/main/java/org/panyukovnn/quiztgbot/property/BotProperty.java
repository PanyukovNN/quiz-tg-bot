package org.panyukovnn.quiztgbot.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotProperty {

    private String BOT_NAME;
    private String BOT_TOKEN;
    private boolean timeLimitEnabled;
}
