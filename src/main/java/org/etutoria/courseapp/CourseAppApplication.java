package org.etutoria.courseapp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.etutoria.courseapp.dao.*;
import org.etutoria.courseapp.entities.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableSpringDataWebSupport
@OpenAPIDefinition(
        info = @Info(
                title = "Course Application REST API Documentation",
                description = "Course App REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Ousmane MBACKE Dev JAVA & Flutter",
                        email = "usmanembacke@gmail.com",
                        url = "https://www.etutoria.org"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.etutoria.org"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Course App REST API Documentation",
                url = "https://www.etutoria.org"
        )
)
public class CourseAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseAppApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CourseDao courseDao, InstructorDao instructorDao,
                                   StudentDao studentDao, UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create roles
            Role adminRole = new Role(null, "ADMIN", new HashSet<>());
            Role instructorRole = new Role(null, "INSTRUCTOR", new HashSet<>());
            Role studentRole = new Role(null, "STUDENT", new HashSet<>());
            roleDao.save(adminRole);
            roleDao.save(instructorRole);
            roleDao.save(studentRole);

            // Create users with encoded passwords
            User adminUser = new User(null, "admin@example.com", passwordEncoder.encode("admin123"), new HashSet<>(), null, null);
            adminUser.getRoles().add(adminRole);
            userDao.save(adminUser);

            User studentUser = new User(null, "student@example.com", passwordEncoder.encode("student123"), new HashSet<>(), null, null);
            studentUser.getRoles().add(studentRole);
            userDao.save(studentUser);

            User instructorUser1 = new User(null, "instructor1@example.com", passwordEncoder.encode("instructor123"), new HashSet<>(), null, null);
            instructorUser1.getRoles().add(instructorRole);
            userDao.save(instructorUser1);

            User instructorUser2 = new User(null, "instructor2@example.com", passwordEncoder.encode("instructor123"), new HashSet<>(), null, null);
            instructorUser2.getRoles().add(instructorRole);
            userDao.save(instructorUser2);

            User instructorUser3 = new User(null, "instructor3@example.com", passwordEncoder.encode("instructor123"), new HashSet<>(), null, null);
            instructorUser3.getRoles().add(instructorRole);
            userDao.save(instructorUser3);

            // Create instructors
            Instructor instructor1 = new Instructor(null, "John", "Doe", "Experienced software engineer.", new HashSet<>(), instructorUser1);
            Instructor instructor2 = new Instructor(null, "Ousmane", "Mbacke", "Professor Coran.", new HashSet<>(), instructorUser2);
            Instructor instructor3 = new Instructor(null, "Nabou", "Bousso", "Experienced software engineer.", new HashSet<>(), instructorUser3);
            instructorDao.save(instructor1);
            instructorDao.save(instructor2);
            instructorDao.save(instructor3);

            // Create student
            Student student = new Student(null, "Jane", "Smith", "Beginner", new HashSet<>(), studentUser);
            studentDao.save(student);

            // Create courses
            Course course1 = new Course(null, "Introduction to Programming", "10 weeks", "This course covers the basics of programming.", instructor1, new HashSet<>());
            Course course2 = new Course(null, "Advanced Java", "8 weeks", "This course covers advanced topics in Java.", instructor1, new HashSet<>());
            Course course3 = new Course(null, "Introduction to Flutter", "6 weeks", "This course covers the basics of Flutter.", instructor2, new HashSet<>());
            Course course4 = new Course(null, "Advanced Flutter", "4 weeks", "This course covers advanced topics in Flutter.", instructor2, new HashSet<>());
            Course course5 = new Course(null, "Introduction to Spring Boot", "6 weeks", "This course covers the basics of Spring Boot.", instructor3, new HashSet<>());
            course1.getStudents().add(student);
            course2.getStudents().add(student);
            ///course3.getStudents().add(student);
            //course4.getStudents().add(student);
            //course5.getStudents().add(student);
            courseDao.save(course1);
            courseDao.save(course2);
            courseDao.save(course3);
            courseDao.save(course4);
           courseDao.save(course5);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}