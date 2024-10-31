package org.etutoria.courseapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "User",
        description = "Schema to hold User information",
        example = "{ \"email\": \"new.user@example.com\", \"password\": \"newpassword123\" }"
)
@Data
public class UserDto {
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    @Size(max = 45, message = "Email must be at most 45 characters")
    @Schema(
            description = "Email of the user", example = "john.doe@example.com"
    )
    private String email;
    @NotEmpty(message = "Password cannot be null or empty")
    @Size(max = 64, message = "Password must be at most 64 characters")
    @Schema(
            description = "Password of the user", example = "password123"
    )
    private String password;
}