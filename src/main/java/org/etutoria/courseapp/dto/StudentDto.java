package org.etutoria.courseapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "Student",
        description = "Schema to hold Student information"
)
@Data
public class StudentDto {

    @Schema(
            description = "ID of the student", example = "1"
    )
    private Long studentId;
    @NotEmpty(message = "First name cannot be null or empty")
    @Size(max = 45, message = "First name must be at most 45 characters")
    @Schema(
            description = "First name of the student", example = "Jane"
    )
    private String firstName;
    @NotEmpty(message = "Last name cannot be null or empty")
    @Size(max = 45, message = "Last name must be at most 45 characters")
    @Schema(
            description = "Last name of the student", example = "Smith"
    )
    private String lastName;

    @NotEmpty(message = "Level cannot be null or empty")
    @Size(max = 64, message = "Level must be at most 64 characters")
    @Schema(
            description = "Level of the student", example = "Beginner"
    )
    private String level;

    @Schema(
            description = "User associated with the student"
    )
    private UserDto user;
}