package org.etutoria.courseapp.service;

import org.etutoria.courseapp.dto.InstructorDto;
import org.etutoria.courseapp.entities.Instructor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstructorService {
    Instructor loadInstructorById(Long instructorId);

    Page<InstructorDto> findInstructorsByName(String name, int page, int size);

    InstructorDto loadInstructorByEmail(String email);

    InstructorDto createInstructor(InstructorDto instructorDTO);

    InstructorDto updateInstructor(InstructorDto instructorDTO);

    List<InstructorDto> fetchInstructors();

    void removeInstructor(Long instructorId);
}
