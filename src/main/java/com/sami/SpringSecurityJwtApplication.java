package com.sami;

import com.sami.entity.AppUser;
import com.sami.entity.Role;
import com.sami.service.AppUserService;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class SpringSecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AppUserService userService) {
        return args -> {
            Collection<Role> roles = new ArrayList<>();
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");
            roles.add(roleAdmin);
            roles.add(roleUser);
            userService.saveRole(roles);
            AppUser user = new AppUser();
            user.setUsername("sami");
            user.setPassword("sami");
            user.setRoles(roles);
            userService.saveUser(user);
        };
    }
}
