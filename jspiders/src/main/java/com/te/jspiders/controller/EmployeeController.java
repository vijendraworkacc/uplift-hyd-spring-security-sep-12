package com.te.jspiders.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.jspiders.dto.EmployeeDto;
import com.te.jspiders.response.ApiResponse;
import com.te.jspiders.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/employee")
@RestController
public class EmployeeController {

	private final EmployeeService employeeService;

	@PostMapping(path = "/register")
	public ApiResponse<String> registerEmployee(@RequestBody EmployeeDto employeeDto) {
		log.info("EmployeeController:registerEmployee execution start, {}", employeeDto);
		Optional<String> empId = employeeService.registerEmployee(employeeDto);
		if (empId.isPresent()) {
			return new ApiResponse<String>("Employee registration successfull!", null, empId.get());
		}
		throw new RuntimeException("Employee registration failed");
	}
}
