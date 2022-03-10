package com.github.peacetrue.result.builder;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.Ordered;

/**
 * 默认的 {@link MessageSourceAutoConfiguration} 配置，
 * 需要类路径具有 messages.properties 时，才能启用。
 * 排除默认的，修改其匹配条件，使其始终能够启用。
 *
 * @author peace
 * @since 1.0
 **/
@Configuration
@ConditionalOnMissingBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, search = SearchStrategy.CURRENT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties
@ImportAutoConfiguration(exclude = MessageSourceAutoConfiguration.class)
public class ResultMessageSourceAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourceProperties messageSourceProperties() {
        MessageSourceProperties properties = new MessageSourceProperties();
        properties.setBasename(null);
        return properties;
    }

    @Bean
    public MessageSource messageSource(MessageSourceProperties properties) {
        return new MessageSourceAutoConfiguration().messageSource(properties);
    }
}
