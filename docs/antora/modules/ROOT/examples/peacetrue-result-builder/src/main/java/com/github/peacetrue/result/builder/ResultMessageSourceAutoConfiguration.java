package com.github.peacetrue.result.builder;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 默认的 {@link MessageSourceAutoConfiguration} 配置，
 * 需要类路径具有 messages.properties 时，才能启用。
 * 排除默认的，修改其匹配条件，使其始终能够启用。
 * 启用之后，动态追加一些配置。
 * <p>
 * 支持 Spring 4 和 Spring 5 版本，完全基于父类实现，所以可以跨版本。
 *
 * @author peace
 **/
@Configuration
@ConditionalOnMissingBean(value = MessageSource.class, search = SearchStrategy.CURRENT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.messages")
@AutoConfigureBefore(MessageSourceAutoConfiguration.class)
@ImportAutoConfiguration(exclude = MessageSourceAutoConfiguration.class)
public class ResultMessageSourceAutoConfiguration extends MessageSourceAutoConfiguration {

}
