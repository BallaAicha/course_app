package org.etutoria.courseapp.service.impl;
import jakarta.persistence.EntityNotFoundException;
import org.etutoria.courseapp.dao.CourseDao;
import org.etutoria.courseapp.dao.InstructorDao;
import org.etutoria.courseapp.dao.StudentDao;
import org.etutoria.courseapp.dto.CourseDto;
import org.etutoria.courseapp.entities.Course;
import org.etutoria.courseapp.entities.Instructor;
import org.etutoria.courseapp.entities.Student;
import org.etutoria.courseapp.mapper.CourseMapper;
import org.etutoria.courseapp.service.ICourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
@Service
@Transactional
public class CourseServiceImpl implements ICourseService {

    private CourseDao courseDao;

    private CourseMapper courseMapper;

    private InstructorDao instructorDao;

    private StudentDao studentDao;

    public CourseServiceImpl(CourseDao courseDao, CourseMapper courseMapper, InstructorDao instructorDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.courseMapper = courseMapper;
        this.instructorDao = instructorDao;
        this.studentDao = studentDao;
    }

    @Override
    public Course loadCourseById(Long courseId) {
        return courseDao.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " Not Found"));
    }

    @Override
    public CourseDto createCourse(CourseDto courseDTO) {
        Course course = courseMapper.fromCourseDto(courseDTO);
        Instructor instructor = instructorDao.findById(courseDTO.getInstructor().getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + courseDTO.getInstructor().getInstructorId() + " Not Found"));
        course.setInstructor(instructor);
        Course savedCourse = courseDao.save(course);
        return courseMapper.fromCourse(savedCourse);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDTO) {
        Course loadedCourse = loadCourseById(courseDTO.getCourseId());
        Instructor instructor = instructorDao.findById(courseDTO.getInstructor().getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + courseDTO.getInstructor().getInstructorId() + " Not Found"));
        Course course = courseMapper.fromCourseDto(courseDTO);
        course.setInstructor(instructor);
        course.setStudents(loadedCourse.getStudents());
        Course updatedCourse = courseDao.save(course);
        return courseMapper.fromCourse(updatedCourse);
    }






    @Override
    public void assignStudentToCourse(Long courseId, Long studentId) {
        Student student = studentDao.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " Not Found"));
        Course course = loadCourseById(courseId);
        course.getStudents().add(student);
        courseDao.save(course);

    }

    @Override
    public void removeEnrollmentsByStudentId(Long studentId) {
        courseDao.removeEnrollmentsByStudentId(studentId);
    }

    @Override
    public Page<CourseDto> findCoursesByCourseName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> coursesPage = courseDao.findCoursesByCourseNameContains(keyword, pageRequest);
        return new PageImpl<>(
                coursesPage.getContent().stream()
                        .map(courseMapper::fromCourse)
                        .collect(Collectors.toList()),
                pageRequest,
                coursesPage.getTotalElements()
        );
    }



    @Override
    public Page<CourseDto> fetchCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> studentCoursesPage = courseDao.getCoursesByStudentId(studentId,pageRequest);
        return new PageImpl<>(studentCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, studentCoursesPage.getTotalElements());
    }

    @Override
    public Page<CourseDto> fetchNonEnrolledInCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> nonEnrolledInCoursesPage = courseDao.getNonEnrolledInCoursesByStudentId(studentId,pageRequest);
        return new PageImpl<>(nonEnrolledInCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, nonEnrolledInCoursesPage.getTotalElements());
    }

    @Override
    public void removeCourse(Long courseId) {
        courseDao.deleteById(courseId);
    }

    @Override
    public Page<CourseDto> fetchCoursesForInstructor(Long instructorId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> instructorCoursesPage = courseDao.getCoursesByInstructorId(instructorId, pageRequest);
        return new PageImpl<>(instructorCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, instructorCoursesPage.getTotalElements());
    }
}