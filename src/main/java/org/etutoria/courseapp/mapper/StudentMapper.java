package org.etutoria.courseapp.mapper;

import org.etutoria.courseapp.dto.StudentDto;
import org.etutoria.courseapp.entities.Student;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    public StudentDto fromStudent(Student student) {
        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student, studentDto);
        return studentDto;
    }

    public Student fromStudentDto(StudentDto studentDto) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        return student;
    }
}