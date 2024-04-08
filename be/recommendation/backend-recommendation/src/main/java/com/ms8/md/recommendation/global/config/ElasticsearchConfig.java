package com.ms8.md.recommendation.global.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

	@Bean
	@Override
	public RestHighLevelClient elasticsearchClient() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
			.connectedTo("j10a208.p.ssafy.io:9200")
			.build();

		return RestClients.create(clientConfiguration)
			.rest();
	}
}