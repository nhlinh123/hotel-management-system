package com.ptit.hotelmanagementsystem.controller;

import com.ptit.hotelmanagementsystem.dto.RegisterRequest;
import com.ptit.hotelmanagementsystem.dto.BaseResponseModel;
import com.ptit.hotelmanagementsystem.dto.UserDto;
import com.ptit.hotelmanagementsystem.model.User;
import com.ptit.hotelmanagementsystem.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponseModel<UserDto>> createUser(@RequestBody RegisterRequest registerRequest) {
        User newUser = userService.createUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseModel.created(UserDto.fromUser(newUser), "User created successfully"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponseModel<List<UserDto>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(BaseResponseModel.success(userDtos));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).orElse(new com.ptit.hotelmanagementsystem.model.User()).getUsername() == authentication.principal.username")
    public ResponseEntity<BaseResponseModel<UserDto>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(BaseResponseModel.success(UserDto.fromUser(user))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseModel.notFound("User not found")));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).orElse(new com.ptit.hotelmanagementsystem.model.User()).getUsername() == authentication.principal.username")
    public ResponseEntity<BaseResponseModel<UserDto>> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(BaseResponseModel.success(UserDto.fromUser(updatedUser), "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponseModel<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(BaseResponseModel.success("User deleted successfully"));
    }
}
