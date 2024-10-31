package org.etutoria.courseapp.mapper;
import org.etutoria.courseapp.dto.CourseDto;
import org.etutoria.courseapp.entities.Course;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service
public class CourseMapper {

    private final InstructorMapper instructorMapper;
    public CourseMapper(InstructorMapper instructorMapper) {
        this.instructorMapper = instructorMapper;
    }
    public CourseDto fromCourse(Course course) {
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(course, courseDto);//Copie des propriétés : Elle copie les valeurs des propriétés de l'objet course vers l'objet courseDto.
        courseDto.setInstructor(instructorMapper.fromInstructor(course.getInstructor()));
        return courseDto;
    }


    public Course fromCourseDto(CourseDto courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        course.setInstructor(instructorMapper.fromInstructorDto(courseDto.getInstructor()));
        return course;
    }
}