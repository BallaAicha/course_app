package org.etutoria.courseapp.service.impl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.etutoria.courseapp.dao.StudentDao;
import org.etutoria.courseapp.dto.StudentDto;
import org.etutoria.courseapp.entities.Course;
import org.etutoria.courseapp.entities.Student;
import org.etutoria.courseapp.entities.User;
import org.etutoria.courseapp.mapper.StudentMapper;
import org.etutoria.courseapp.service.StudentService;
import org.etutoria.courseapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Iterator;
import java.util.stream.Collectors;
@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImpl  implements StudentService {
    private StudentDao studentDao;
    private StudentMapper studentMapper;
    private UserService userService;
    @Override
    public Student loadStudentById(Long studentId) {
        return studentDao.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found"));
    }
    @Override
    public Page<StudentDto> loadStudentsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Student> studentsPage = studentDao.findStudentsByName(name, pageRequest);
        return new PageImpl<>(studentsPage.getContent().stream().map(student -> studentMapper.fromStudent(student)).collect(Collectors.toList()), pageRequest, studentsPage.getTotalElements());
    }

    @Override
    public StudentDto loadStudentByEmail(String email) {
        return studentMapper.fromStudent(studentDao.findStudentByEmail(email));
    }

    @Override
    public StudentDto createStudent(StudentDto studentDTO) {
        User user = userService.createUser(studentDTO.getUser().getEmail(), studentDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(), "Student");
        Student student = studentMapper.fromStudentDto(studentDTO);
        student.setUser(user);
        Student savedStudent = studentDao.save(student);
        return studentMapper.fromStudent(savedStudent);

    }

    @Override
    public StudentDto updateStudent(StudentDto studentDTO) {
        Student loadedStudent = loadStudentById(studentDTO.getStudentId());
        Student student = studentMapper.fromStudentDto(studentDTO);
        student.setUser(loadedStudent.getUser());
        student.setCourses(loadedStudent.getCourses());
        Student updatedStudent = studentDao.save(student);
        return studentMapper.fromStudent(updatedStudent);
    }

    @Override
    public void removeStudent(Long studentId) {
        Student student = loadStudentById(studentId);
        Iterator<Course> courseIterator = student.getCourses().iterator();//recuperer les cours de l'etudiant
        if (courseIterator.hasNext()) {//verifier si l'etudiant est inscrit a un cours
            Course course = courseIterator.next(); //recuperer le cours
            course.removeStudentFromCourse(student);//supprimer l'etudiant du cours
        }
        studentDao.deleteById(studentId);

    }
}
