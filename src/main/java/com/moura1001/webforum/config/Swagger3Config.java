package com.moura1001.webforum.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger3Config {
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("webforum-public")
                .pathsToMatch("/forum/api/v1/**")
                .build();
    }
}
