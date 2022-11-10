package com.te.jspiders.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.te.jspiders.dto.EmployeeDto;
import com.te.jspiders.dto.StudentDto;
import com.te.jspiders.entity.AppUser;
import com.te.jspiders.entity.Employee;
import com.te.jspiders.entity.Roles;
import com.te.jspiders.entity.Student;
import com.te.jspiders.repository.AppUserRepository;
import com.te.jspiders.repository.RoleRepository;
import com.te.jspiders.repository.StudentRepository;
import com.te.jspiders.service.StudentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final RoleRepository roleRepository;
	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<String> registerStudent(StudentDto studentDto) {
		log.info("EmployeeServiceImpl:registerEmployee execution start, {}", studentDto);
		Student student = new Student();
		BeanUtils.copyProperties(studentDto, student);
		log.debug("StudentServiceImpl:registerStudent, employee entity object created {}", student);

		Optional<Roles> employeeRole = roleRepository.findByRoleName("ROLE_STUDENT");
		if (employeeRole.isPresent()) {
			Roles roles = employeeRole.get();
			AppUser appUser = AppUser.builder().username(student.getStudentId())
					.password(passwordEncoder.encode(studentDto.getPassword())).roles(Lists.newArrayList()).build();
			roles.getAppUsers().add(appUser);
			appUser.getRoles().add(roles);
			appUserRepository.save(appUser);
		}
		return Optional.ofNullable(studentRepository.save(student).getStudentId());
	}
}
