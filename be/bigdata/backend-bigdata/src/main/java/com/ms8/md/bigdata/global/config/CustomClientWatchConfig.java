package com.ms8.md.bigdata.global.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.context.refresh.ConfigDataContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ms8.md.bigdata.global.watch.CustomConfigClientWatch;

@Configuration
public class CustomClientWatchConfig {
	@Bean
	@ConditionalOnMissingBean(ConfigServicePropertySourceLocator.class)
	@ConditionalOnProperty(name = ConfigClientProperties.PREFIX + ".enabled", matchIfMissing = true)
	public ConfigServicePropertySourceLocator configServicePropertySource(ConfigClientProperties properties) {
		return new ConfigServicePropertySourceLocator(properties);
	}

	@Bean
	@ConditionalOnProperty(name = ConfigClientProperties.PREFIX + ".enabled", matchIfMissing = true)
	public CustomConfigClientWatch configGitClientWatch(ConfigDataContextRefresher refresher, ConfigServicePropertySourceLocator locator) {
		return new CustomConfigClientWatch(refresher, locator);
	}
}
