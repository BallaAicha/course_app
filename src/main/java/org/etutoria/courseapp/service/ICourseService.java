package org.etutoria.courseapp.service;

import org.etutoria.courseapp.dto.CourseDto;
import org.etutoria.courseapp.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICourseService {
    Course loadCourseById(Long courseId);
    CourseDto createCourse(CourseDto courseDTO);
    CourseDto updateCourse(CourseDto courseDTO);
    Page<CourseDto> findCoursesByCourseName(String keyword, int page, int size);
    Page<CourseDto> fetchCoursesForStudent(Long studentId,int page, int size);
    Page<CourseDto> fetchNonEnrolledInCoursesForStudent(Long studentId, int page, int size);
    void removeCourse(Long courseId);
    Page<CourseDto> fetchCoursesForInstructor(Long instructorId, int page, int size);
    void assignStudentToCourse(Long courseId, Long studentId);

    void removeEnrollmentsByStudentId(Long studentId);
}