package org.etutoria.courseapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Schema(
        name = "Role",
        description = "Schema to hold Role information"
)
@Data
public class RoleDto {

    @Schema(
            description = "ID of the role", example = "1"
    )
    private Long roleId;
    @NotEmpty(message = "Role name cannot be null or empty")
    @Size(max = 45, message = "Role name must be at most 45 characters")
    @Schema(
            description = "Name of the role", example = "ADMIN"
    )
    private String name;

    @Schema(
            description = "Users associated with the role"
    )
    private Set<UserDto> users;
}