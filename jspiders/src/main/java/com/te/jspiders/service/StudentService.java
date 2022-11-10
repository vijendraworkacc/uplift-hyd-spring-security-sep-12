package com.te.jspiders.service;

import java.util.Optional;

import com.te.jspiders.dto.StudentDto;

public interface StudentService {

	Optional<String> registerStudent(StudentDto studentDto);

}
