package com.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

	@Value("${spring.redis.database}")
	public int indexDatabase;

	@Value("${spring.redis.host}")
	public String hostName;

	@Value("${spring.redis.port}")
	public int port;

	@Value("${spring.redis.password}")
	private String password;
	
	@Bean
    public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration standaloneConfiguration = new  RedisStandaloneConfiguration();
		
		standaloneConfiguration.setDatabase(indexDatabase);
		standaloneConfiguration.setHostName(hostName);
		standaloneConfiguration.setPassword(password);
		standaloneConfiguration.setPort(port);
		
        return new LettuceConnectionFactory(standaloneConfiguration);
    }

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;

	}
}
