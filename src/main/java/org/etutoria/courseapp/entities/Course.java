package org.etutoria.courseapp.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courses")
public class Course extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long courseId;
    @Basic
    @Column(name = "course_name", nullable = false, length = 45)
    private String courseName;
    @Basic
    @Column(name ="course_duration", nullable = false, length = 45)
    private String courseDuration;
    @Basic
    @Column(name="course_description", nullable = false, length = 64)
    private String courseDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id",referencedColumnName = "instructor_id",nullable = false)
    private Instructor instructor;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "enrolled_in",
            joinColumns = {@JoinColumn(name="course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private Set<Student> students = new HashSet<>();


    public void removeStudentFromCourse(Student student) {
        this.students.remove(student);
        student.getCourses().remove(this);//le this represente l'instance de la classe cad le cours
    }
}
