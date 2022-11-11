package com.te.jspiders.controller;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.jspiders.dto.EmployeeDto;
import com.te.jspiders.dto.LoginDto;
import com.te.jspiders.dto.StudentDto;
import com.te.jspiders.dto.TrainerDto;
import com.te.jspiders.response.ApiResponse;
import com.te.jspiders.service.EmployeeService;
import com.te.jspiders.service.StudentService;
import com.te.jspiders.service.TrainerService;
import com.te.jspiders.utils.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * We created a controller which has all the methods, supposed to be public and can be 
 * accessed with a common path.
 * 
 * And other controllers are very specific to the type of the user.
 * 
 * After having public apis we tried registering users, and admin using CommandLineRunner.
 * */
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/public")
@RestController
public class ApplicationController {

	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;
	private final EmployeeService employeeService;
	private final StudentService studentService;
	private final TrainerService trainerService;

	@PostMapping(path = "/trainer/register")
	public ApiResponse<String> registerTrainer(@RequestBody TrainerDto trainerDto) {
		log.info("TrainerController:registerTrainer execution start, {}", trainerDto);
		Optional<String> trainerId = trainerService.registerTrainer(trainerDto);
		if (trainerId.isPresent()) {
			return new ApiResponse<String>("Trainer registration successfull!", null, trainerId.get());
		}
		throw new RuntimeException("Trainer registration failed");
	}

	@PostMapping(path = "/student/register")
	public ApiResponse<String> registerStudent(@RequestBody StudentDto studentDto) {
		log.info("StudentController:registerStudent execution start, {}", studentDto);
		Optional<String> stuId = studentService.registerStudent(studentDto);
		if (stuId.isPresent()) {
			return new ApiResponse<String>("Student registration successfull!", null, stuId.get());
		}
		throw new RuntimeException("Student registration failed");
	}

	@PostMapping(path = "/employee/register")
	public ApiResponse<String> registerEmployee(@RequestBody EmployeeDto employeeDto) {
		log.info("EmployeeController:registerEmployee execution start, {}", employeeDto);
		Optional<String> empId = employeeService.registerEmployee(employeeDto);
		if (empId.isPresent()) {
			return new ApiResponse<String>("Employee registration successfull!", null, empId.get());
		}
		throw new RuntimeException("Employee registration failed");
	}

	/*
	 * Using this api we first tried to generate the token without authentication
	 * and only by using the username, and also sending it in the response. And
	 * later we using AuthenticationManager object to call authenticate() method and
	 * pass UsernamePasswordAuthenticationToken object containing username and
	 * password.
	 */
	@PostMapping(path = "/login")
	public ApiResponse<Object> login(@RequestBody LoginDto loginDto) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		String token = jwtUtils.generateToken(loginDto.getUsername());
		return new ApiResponse<Object>("Login successfull!", token, null);
	}
}
