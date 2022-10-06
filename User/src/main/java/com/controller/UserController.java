package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.OrderStatus;
import com.dto.OrderDTO;
import com.request.LoginRequest;
import com.service.IUserService;

@RestController
@RequestMapping("/api/v1/customer")
public class UserController implements IUserController {

	private final String orderToken = "orderToken";

	@Autowired
	IUserService userService;

	@Override
	public ResponseEntity<String> orderProduct(@Valid OrderDTO order) {
		String token = userService.orderProduct(order);
		HttpHeaders header = new HttpHeaders();
		header.set(orderToken, token);

		return new ResponseEntity<String>(OrderStatus.PROCESS, header, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<String> login(@Valid LoginRequest request) {
		// TODO Auto-generated method stub
		return new ResponseEntity<String>("Ok", HttpStatus.OK);
	}

}
