package com.sami.service;

import com.sami.entity.AppUser;
import com.sami.entity.Role;
import com.sami.repository.AppUserRepository;
import com.sami.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public List<AppUser> findAll() {
        List<AppUser> users = appUserRepository.findAll();
        log.info("getting all users {} ", users);
        return users;
    }

    @Transactional
    public void saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("save user {} ", user);
        appUserRepository.save(user);
    }

    @Transactional
    public void saveRole(Collection<Role> roles) {
        log.info("getting all roles {} ", roles);
        roleRepository.saveAll(roles);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
