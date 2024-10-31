package org.etutoria.courseapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "Instructor",
        description = "Schema to hold Instructor information"
)
@Data
public class InstructorDto {

    @Schema(
            description = "ID of the instructor", example = "1"
    )
    private Long instructorId;

    @NotEmpty(message = "First name cannot be null or empty")
    @Size(max = 45, message = "First name must be at most 45 characters")
    @Schema(
            description = "First name of the instructor", example = "John"
    )
    private String firstName;

    @NotEmpty(message = "Last name cannot be null or empty")
    @Size(max = 45, message = "Last name must be at most 45 characters")
    @Schema(
            description = "Last name of the instructor", example = "Doe"
    )
    private String lastName;

    @NotEmpty(message = "Summary cannot be null or empty")
    @Size(max = 255, message = "Summary must be at most 255 characters")
    @Schema(
            description = "Summary of the instructor", example = "Experienced software engineer."
    )
    private String summary;

    @Schema(
            description = "User associated with the instructor"
    )
    private UserDto user;
}