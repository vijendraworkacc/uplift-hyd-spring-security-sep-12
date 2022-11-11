package com.te.jspiders.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.jspiders.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/auth/trainer")
@RestController
public class TrainerController {

	@PutMapping(path = "/update")
	public ApiResponse<String> updateTrainer() {
		return new ApiResponse<String>("Trainer update successfull!", null, "Update api being used");
	}

	@DeleteMapping(path = "/delete")
	public ApiResponse<String> deleteTrainer() {
		return new ApiResponse<String>("Trainer delete successfull!", null, "Delete api being used");
	}

	@PutMapping(path = "/changePassword")
	public ApiResponse<String> changePassword() {
		return new ApiResponse<String>("Trainer change password successfull!", null, "Change password api being used");
	}
}
