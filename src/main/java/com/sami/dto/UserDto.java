package com.sami.dto;

import com.sami.entity.AppUser;
import com.sami.entity.Role;
import lombok.Data;

import java.util.Collection;

@Data
public class UserDto {

    private Long id;

    private String username;

    private Collection<Role> roles;

    public static UserDto select(AppUser user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
