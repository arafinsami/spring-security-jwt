package com.sami.controller;

import com.sami.dto.UserDto;
import com.sami.helper.AppUserHelper;
import com.sami.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.sami.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "user api")
public record UserController(AppUserService userService,
                             AppUserHelper helper) {

    @GetMapping("/lists")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "show lists of users", description = "lists of users")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    })
    public ResponseEntity<JSONObject> findAll() {
        List<UserDto> users = userService.findAll().stream()
                .map(UserDto::select)
                .collect(Collectors.toList());
        return ok(success(users).getJson());
    }

    @GetMapping("/generate/refresh_token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        helper.generateAccessToken(request, response);
    }
}
