package com.example.crud.service;

import com.example.crud.dto.UserDTO;
import com.example.crud.entity.User;
import com.example.crud.exception.DuplicateResourceException;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for User operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * Create a new user
     * @param userDTO the user data transfer object
     * @return saved user DTO
     */
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating new user with email: {}", userDTO.getEmail());

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("User with email " + userDTO.getEmail() + " already exists");
        }

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .build();

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());

        return convertToDTO(savedUser);
    }

    /**
     * Get user by ID
     * @param id the user ID
     * @return user DTO
     */
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        return convertToDTO(user);
    }

    /**
     * Get all users
     * @return list of user DTOs
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");

        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all users with pagination
     * @param pageable pagination information
     * @return page of user DTOs
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsersWithPagination(Pageable pageable) {
        log.info("Fetching users with pagination");

        return userRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    /**
     * Update user
     * @param id the user ID
     * @param userDTO the updated user data
     * @return updated user DTO
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Check if email is being changed and if new email already exists
        if (!user.getEmail().equals(userDTO.getEmail()) && 
            userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("User with email " + userDTO.getEmail() + " already exists");
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", id);

        return convertToDTO(updatedUser);
    }

    /**
     * Delete user by ID
     * @param id the user ID
     */
    public void deleteUserById(Long id) {
        log.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

    /**
     * Get user by email
     * @param email the user email
     * @return user DTO
     */
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return convertToDTO(user);
    }

    /**
     * Convert User entity to UserDTO
     * @param user the user entity
     * @return user DTO
     */
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }
}
