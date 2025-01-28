package com.teamhide.template.clients.pgclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class PgClientFactory {
    @Value("${clients.pg-client.base-url}")
    private String baseUrl;

    @Bean
    public PgClient pgClient() {
        final RestClient restClient = RestClient.builder().baseUrl(baseUrl).baseUrl(baseUrl).build();
        final RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        final HttpServiceProxyFactory proxyFactory =
                HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return proxyFactory.createClient(PgClient.class);
    }
}
