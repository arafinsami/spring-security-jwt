package com.sami.controller;

import com.sami.dto.UserDto;
import com.sami.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.sami.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@Tag(name = "user api")
public record UserController(AppUserService userService) {

    @GetMapping("/lists")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "show lists of users", description = "lists of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found users", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
            })
    })
    public ResponseEntity<JSONObject> findAll() {
        List<UserDto> users = userService.findAll().stream()
                .map(UserDto::select)
                .collect(Collectors.toList());
        return ok(success(users).getJson());
    }
}
