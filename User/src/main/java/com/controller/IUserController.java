package com.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dto.OrderDTO;
import com.request.LoginRequest;

public interface IUserController {
	
	@PostMapping("/order")
	ResponseEntity<String> orderProduct(@RequestBody @Valid OrderDTO order);
	
	@PostMapping("/login")
	ResponseEntity<String>  login(@RequestBody @Valid LoginRequest request);
	
}
