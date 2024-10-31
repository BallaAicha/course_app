package org.etutoria.courseapp.mapper;
import org.etutoria.courseapp.dto.InstructorDto;
import org.etutoria.courseapp.entities.Instructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service
public class InstructorMapper {

    public InstructorDto fromInstructor(Instructor instructor) {
        InstructorDto instructorDto = new InstructorDto();
        BeanUtils.copyProperties(instructor, instructorDto);
        return instructorDto;
    }

    public Instructor fromInstructorDto(InstructorDto instructorDto) {
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDto, instructor);
        return instructor;
    }
}