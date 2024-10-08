/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.UserLoginRequestDTO;
import main.dto.UserLoginResponseDTO;
import main.dto.UserRegistrationRequestDTO;
import main.dto.UserRegistrationResponseDTO;
import main.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/auth")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@Tag(name="User Authentication", description=" User Auth Controller")
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class UserAuthenticationController {
      UserService service;
    
    
    @Operation(summary="Register a new  User")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="User is successfully Registered"),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body")
    })
    @PostMapping(value="/signup",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegistrationResponseDTO> registerUser(
            @Valid @RequestBody UserRegistrationRequestDTO x){
        
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registerUser(x));
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping(value="/login",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDTO> loginUser(
            @Parameter(description = "User request to login", required = true)
            @RequestBody @Valid UserLoginRequestDTO x
    ){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.loginUser(x));
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    
    
    @Operation(summary="Register a new  Admin")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Admin is successfully Registered"),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body")
    })
    @PostMapping(value="/admin/register",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<UserRegistrationResponseDTO> registerAdmin(
            @Parameter(description = "admin details to register", required = true)
            @Valid @RequestBody UserRegistrationRequestDTO x){
        
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registerAdmin(x));
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
