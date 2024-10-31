package org.etutoria.courseapp.service.impl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.etutoria.courseapp.dao.InstructorDao;
import org.etutoria.courseapp.dto.InstructorDto;
import org.etutoria.courseapp.entities.Course;
import org.etutoria.courseapp.entities.Instructor;
import org.etutoria.courseapp.entities.User;
import org.etutoria.courseapp.mapper.InstructorMapper;
import org.etutoria.courseapp.service.ICourseService;
import org.etutoria.courseapp.service.InstructorService;
import org.etutoria.courseapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private InstructorDao instructorDao;

    private InstructorMapper instructorMapper;

    private UserService userService;

    private ICourseService courseService;
    @Override
    public Instructor loadInstructorById(Long instructorId) {
        return instructorDao.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instructor with ID" + instructorId + " not found"));
    }

    @Override
    public Page<InstructorDto> findInstructorsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Instructor> instructorsPage = instructorDao.findInstructorsByName(name, pageRequest);
        return new PageImpl<>(instructorsPage.getContent().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList()), pageRequest, instructorsPage.getTotalElements());
    }

    @Override
    public InstructorDto loadInstructorByEmail(String email) {
        return instructorMapper.fromInstructor(instructorDao.findInstructorByEmail(email));
    }

    @Override
    public InstructorDto createInstructor(InstructorDto instructorDTO) {
        User user = userService.createUser(instructorDTO.getUser().getEmail(),instructorDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(),"INSTRUCTOR");
        Instructor instructor = instructorMapper.fromInstructorDto(instructorDTO);
        instructor.setUser(user);
        Instructor savedInstructor = instructorDao.save(instructor);
        return instructorMapper.fromInstructor(savedInstructor);
    }

    @Override
    public InstructorDto updateInstructor(InstructorDto instructorDTO) {
        Instructor loadedInstructor = loadInstructorById(instructorDTO.getInstructorId());
        Instructor instructor = instructorMapper.fromInstructorDto(instructorDTO);
        instructor.setUser(loadedInstructor.getUser());
        instructor.setCourses(loadedInstructor.getCourses());
        Instructor updatedInstructor = instructorDao.save(instructor);
        return instructorMapper.fromInstructor(updatedInstructor);
    }

    @Override
    public List<InstructorDto> fetchInstructors() {
        return instructorDao.findAll().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList());
    }

    @Override
    public void removeInstructor(Long instructorId) {
        Instructor instructor = loadInstructorById(instructorId);
        for(Course course : instructor.getCourses()) {
            courseService.removeCourse(course.getCourseId());
        }
        instructorDao.deleteById(instructorId);
    }

    }


