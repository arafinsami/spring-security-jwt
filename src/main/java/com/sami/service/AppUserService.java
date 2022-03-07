package com.sami.service;

import com.sami.entity.AppUser;
import com.sami.entity.Role;
import com.sami.repository.RoleRepository;
import com.sami.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public List<AppUser> findAll() {
        List<AppUser> users = userRepository.findAll();
        return users;
    }

    public void saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveRole(Collection<Role> roles) {
        roleRepository.saveAll(roles);
    }
}
