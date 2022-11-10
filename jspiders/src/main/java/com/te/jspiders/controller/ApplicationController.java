package com.te.jspiders.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.jspiders.dto.LoginDto;
import com.te.jspiders.response.ApiResponse;
import com.te.jspiders.utils.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/api")
@RestController
public class ApplicationController {

	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;

	@PostMapping(path = "/login")
	public ApiResponse<Object> login(@RequestBody LoginDto loginDto) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
				);
		String token = jwtUtils.generateToken(loginDto.getUsername());
		return new ApiResponse<Object>("Login successfull!", token, null);
	}
}
