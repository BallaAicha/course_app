package org.etutoria.courseapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;


@Schema(
        name = "Course",
        description = "Schema to hold Course information"
)
@Data
public class CourseDto extends RepresentationModel<CourseDto> {

    @Schema(
            description = "ID of the course", example = "1"
    )
    private Long courseId;

    @NotEmpty(message = "Course name cannot be null or empty")
    @Size(max = 45, message = "Course name must be at most 45 characters")
    @Schema(
            description = "Name of the course", example = "Introduction to Programming"
    )
    private String courseName;

    @NotEmpty(message = "Course duration cannot be null or empty")
    @Size(max = 45, message = "Course duration must be at most 45 characters")
    @Schema(
            description = "Duration of the course", example = "10 weeks"
    )
    private String courseDuration;

    @NotEmpty(message = "Course description cannot be null or empty")
    @Size(max = 64, message = "Course description must be at most 64 characters")
    @Schema(
            description = "Description of the course", example = "This course covers the basics of programming."
    )
    private String courseDescription;

    @Schema(
            description = "Instructor of the course"
    )
    private InstructorDto instructor;
}