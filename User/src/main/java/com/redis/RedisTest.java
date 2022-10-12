package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTest implements CommandLineRunner {

	@Autowired
	private RedisTemplate<String, Object> template;

	@Override
	public void run(String... args) throws Exception {
		Object b = new String("sss");
		
		template.opsForValue().set("hi", b);
		System.out.println("Value of key loda: "+template.opsForValue().get("hi"));	
	}

}
