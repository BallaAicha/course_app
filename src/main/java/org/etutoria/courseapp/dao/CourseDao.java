package org.etutoria.courseapp.dao;

import org.etutoria.courseapp.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface CourseDao extends JpaRepository<Course, Long> {
    Page<Course> findCoursesByCourseNameContains(String keyword, Pageable pageable);
    @Query(value = "select * from courses where course_id in (select e.course_id from enrolled_in as e where e.student_id=:studentId)", nativeQuery = true)
    Page<Course> getCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);
    @Query(value = "select * from courses where course_id not in (select e.course_id from enrolled_in as e where e.student_id=:studentId)", nativeQuery = true)
    Page<Course> getNonEnrolledInCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);

    @Query(value = "select c from Course as c where c.instructor.instructorId=:instructorId")//selectione moi les cours dont l'instructeur a l'id donné
    Page<Course> getCoursesByInstructorId(@Param("instructorId") Long instructorId, Pageable pageable);


    @Modifying
    @Query(value = "delete from enrolled_in where student_id=:studentId", nativeQuery = true)
    void removeEnrollmentsByStudentId(@Param("studentId") Long studentId);

}