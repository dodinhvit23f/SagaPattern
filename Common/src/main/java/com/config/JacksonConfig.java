package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;

public class JacksonConfig {
	@Bean
	public Jackson2ObjectMapperBuilder jsonCustomizer() {
		return new Jackson2ObjectMapperBuilder() .serializationInclusion(JsonInclude.Include.NON_NULL)
				.serializationInclusion(JsonInclude.Include.NON_EMPTY);
	}
}
