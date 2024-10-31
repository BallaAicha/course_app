package org.etutoria.courseapp.service;
import org.etutoria.courseapp.dto.StudentDto;

public interface IStudentService {
    StudentDto createStudent(StudentDto studentDto);
}