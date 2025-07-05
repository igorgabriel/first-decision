package com.firstdecision.rh_api.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firstdecision.rh_api.user.dto.UserDTO;
import com.firstdecision.rh_api.user.model.User;
import com.firstdecision.rh_api.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User create(UserDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(encodePassword(dto.getPassword()));

        return repository.save(user);

    }

    @Transactional()
    public User update(Long id, UserDTO dto) {
        User user = findById(id);

        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (repository.existsByEmail(dto.getEmail()) && !user.getEmail().equals(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        user.setPassword(encodePassword(dto.getPassword()));

        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        repository.deleteById(id);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
