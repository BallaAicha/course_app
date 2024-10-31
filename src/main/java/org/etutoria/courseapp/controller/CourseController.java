package org.etutoria.courseapp.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.etutoria.courseapp.dto.CourseDto;
import org.etutoria.courseapp.dto.ErrorResponseDto;
import org.etutoria.courseapp.service.ICourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Courses",
        description = "CRUD REST APIs in Course-app to CREATE, UPDATE, FETCH AND DELETE course details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CourseController {
    private final ICourseService courseService;

    @Operation(
            summary = "Fetch Course Details REST API",
            description = "REST API to fetch course details inside Course-app"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<EntityModel<CourseDto>> searchCourses(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Pageable pageable,
            PagedResourcesAssembler<CourseDto> assembler) {
        Page<CourseDto> coursesPage = courseService.findCoursesByCourseName(keyword, page, size);
        return assembler.toModel(coursesPage);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin')")
    public Page<CourseDto> searchCourses(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size) {
        return courseService.findCoursesByCourseName(keyword, page, size);
    }

    @Operation(
            summary = "Delete Course Details REST API",
            description = "REST API to delete Course details based on a courseId"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCourse(@PathVariable Long courseId) {
        courseService.removeCourse(courseId);
    }

    @Operation(
            summary = "Create Course REST API",
            description = "REST API to create a new course inside Course-app"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public CourseDto saveCourse(@RequestBody CourseDto courseDTO) {
        return courseService.createCourse(courseDTO);
    }

    @Operation(
            summary = "Update  Course REST API",
            description = "REST API to update   course inside Course-app"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{courseId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public CourseDto updateCourse(
            @RequestBody CourseDto courseDTO,
            @PathVariable Long courseId) {
        courseDTO.setCourseId(courseId);
        return courseService.updateCourse(courseDTO);
    }


    @Operation(
            summary = "Assign  Course REST API",
            description = "REST API to assign   course to Students "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/{courseId}/enroll/students/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void enrollStudentInCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.assignStudentToCourse(courseId, studentId);
    }

}