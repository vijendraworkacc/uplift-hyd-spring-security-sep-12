package com.te.jspiders.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.jspiders.dto.StudentDto;
import com.te.jspiders.response.ApiResponse;
import com.te.jspiders.service.StudentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/student")
@RestController
public class StudentController {

	private final StudentService studentService;

	@PostMapping(path = "/register")
	public ApiResponse<String> registerStudent(@RequestBody StudentDto studentDto) {
		log.info("StudentController:registerStudent execution start, {}", studentDto);
		Optional<String> stuId = studentService.registerStudent(studentDto);
		if (stuId.isPresent()) {
			return new ApiResponse<String>("Student registration successfull!", null, stuId.get());
		}
		throw new RuntimeException("Student registration failed");
	}
}
