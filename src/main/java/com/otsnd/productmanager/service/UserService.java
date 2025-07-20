package com.otsnd.productmanager.service;

import com.otsnd.productmanager.controller.utils.DTOMapper;
import com.otsnd.productmanager.dto.response.UserDTO;
import com.otsnd.productmanager.entity.User;
import com.otsnd.productmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<UserDTO> findById(long id) {
        return repository.findById(id).map(DTOMapper::mapUserDTO);
    }
    public Optional<User> findEntityById(long id) {
        return repository.findById(id);
    }
}
