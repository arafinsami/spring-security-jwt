package com.sami.controller;

import com.sami.dto.UserDto;
import com.sami.service.AppUserService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(path = "user")
@RequiredArgsConstructor
public class UserController {

    private final AppUserService userService;

    @GetMapping("/lists")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<JSONObject> findById() {
        List<UserDto> users = userService.findAll().stream()
                .map(UserDto::select)
                .collect(Collectors.toList());
        return ok(success(users).getJson());
    }
}
