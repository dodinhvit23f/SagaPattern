package com.service;

import org.apache.kafka.common.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.OrderStatus;
import com.dto.OrderDTO;
import com.kafka.KafkaBuyRequestHandler;
import com.request.LoginRequest;

@Service
public class UserService implements IUserService {

	@Autowired
	private KafkaBuyRequestHandler buyRequestHandler;

	@Override
	public String login(LoginRequest request) {

		return null;
	}

	@Override
	public String orderProduct(OrderDTO order) {

		String key = Uuid.randomUuid().toString();
		order.setStatus(OrderStatus.PROCESS);
		buyRequestHandler.sendOrder(order, key);
		return key;
	}

}
