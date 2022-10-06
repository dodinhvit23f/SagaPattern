package com.service;

import com.dto.OrderDTO;
import com.request.LoginRequest;

public interface IUserService {
	String login(LoginRequest request);
	
	String orderProduct(OrderDTO order);
	
}
