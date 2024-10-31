package org.etutoria.courseapp.service;

import org.etutoria.courseapp.dto.StudentDto;
import org.etutoria.courseapp.entities.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
    Student loadStudentById(Long studentId);

    Page<StudentDto> loadStudentsByName(String name, int page, int size);

    StudentDto loadStudentByEmail(String email);

    StudentDto createStudent(StudentDto studentDTO);

    StudentDto updateStudent(StudentDto studentDTO);

    void removeStudent(Long studentId);
}
