package com.service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.kafka.common.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.OrderStatus;
import com.dto.OrderDTO;
import com.kafka.KafkaBuyRequestHandler;
import com.request.LoginRequest;

@Service
public class UserService implements IUserService {

	public static final Queue <String> QUEUE = new ConcurrentLinkedQueue<>();
	public static final Map<String, Long> REUQUEST_TIME = new ConcurrentHashMap<>(0);
	public static final Map<String, OrderDTO> ORDER_REQUEST = new ConcurrentHashMap<>(0);
	
	@Autowired
	private KafkaBuyRequestHandler buyRequestHandler;

	@Override
	public String login(LoginRequest request) {
		return null;
	}

	@Override
	public String orderProduct(OrderDTO order) {
		final int maxlength = 40;
		String key = Uuid.randomUuid().toString();
		order.setStatus(OrderStatus.PROCESS);
		
		if (key.length() > maxlength) {
			order.setId(key.substring(0, maxlength));
		}

		buyRequestHandler.sendOrder(order, key);
		QUEUE.add(key);
		REUQUEST_TIME.put(key, System.currentTimeMillis());
		ORDER_REQUEST.put(key, order);
		
		return key;
	}

}
