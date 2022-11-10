package com.te.jspiders.service;

import java.util.Optional;

import com.te.jspiders.dto.EmployeeDto;

public interface EmployeeService {

	Optional<String> registerEmployee(EmployeeDto employeeDto);
	
}
