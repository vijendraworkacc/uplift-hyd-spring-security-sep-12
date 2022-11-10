package com.te.jspiders.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.jspiders.dto.TrainerDto;
import com.te.jspiders.response.ApiResponse;
import com.te.jspiders.service.EmployeeService;
import com.te.jspiders.service.StudentService;
import com.te.jspiders.service.TrainerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/trainer")
@RestController
public class TrainerController {

	private final TrainerService trainerService;

	@PostMapping(path = "/register")
	public ApiResponse<String> registerTrainer(@RequestBody TrainerDto trainerDto) {
		log.info("TrainerController:registerTrainer execution start, {}", trainerDto);
		Optional<String> trainerId = trainerService.registerTrainer(trainerDto);
		if (trainerId.isPresent()) {
			return new ApiResponse<String>("Student registration successfull!", null, trainerId.get());
		}
		throw new RuntimeException("Student registration failed");
	}
}
