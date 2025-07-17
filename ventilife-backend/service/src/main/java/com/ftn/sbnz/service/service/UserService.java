package com.ftn.sbnz.service.service;

import com.ftn.sbnz.service.dto.UserDTO;
import com.ftn.sbnz.service.mapper.UserMapper;
import com.ftn.sbnz.service.model.Role;
import com.ftn.sbnz.service.model.User;
import com.ftn.sbnz.service.repository.RoleRepository;
import com.ftn.sbnz.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email){ return userRepository.findByEmail(email);}

    public User save(User user){
        return userRepository.save(user);
    }

    public UserDTO register(UserDTO userDTO) {
        User createdUser = userMapper.dtoToEntity(userDTO);
        List<Role> roles = new ArrayList<>(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        createdUser.setRoles(roles);
        createdUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User user = save(createdUser);
        return userMapper.entityToDto(user);
    }
}
