package com.te.jspiders.service.impl;

import java.util.Optional;

import javax.management.relation.Role;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.te.jspiders.dto.EmployeeDto;
import com.te.jspiders.entity.AppUser;
import com.te.jspiders.entity.Employee;
import com.te.jspiders.entity.Roles;
import com.te.jspiders.repository.AppUserRepository;
import com.te.jspiders.repository.EmployeeRepository;
import com.te.jspiders.repository.RoleRepository;
import com.te.jspiders.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final RoleRepository roleRepository;
	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<String> registerEmployee(EmployeeDto employeeDto) {
		log.info("EmployeeServiceImpl:registerEmployee execution start, {}", employeeDto);
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDto, employee);
		log.debug("EmployeeServiceImpl:registerEmployee, employee entity object created {}", employee);
		Optional<Roles> employeeRole = roleRepository.findByRoleName("ROLE_EMPLOYEE");
		if (employeeRole.isPresent()) {
			log.debug("EmployeeServiceImpl:registerEmployee, role found in database");
			Roles roles = employeeRole.get();
			AppUser appUser = AppUser.builder().username(employee.getEmployeeId())
					.password(passwordEncoder.encode(employeeDto.getPassword())).roles(Lists.newArrayList()).build();
			roles.getAppUsers().add(appUser);
			appUser.getRoles().add(roles);
			appUserRepository.save(appUser);
			log.info("EmployeeServiceImpl:registerEmployee, registraction done");
		}
		log.info("EmployeeServiceImpl:registerEmployee returning the data");
		return Optional.ofNullable(employeeRepository.save(employee).getEmployeeId());
	}
}
