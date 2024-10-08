/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import main.dto.UserRegistrationRequestDTO;
import main.dto.UserRegistrationResponseDTO;
import main.dto.UserResponse;
import main.models.User;
import main.repo.RoleRepo;
import main.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class UserMapper {
    @Autowired
    public UserMapper(@Lazy PasswordEncoder encoder, RoleRepo repo) {
        this.encoder = encoder;
        this.repo = repo;
    }
    private final PasswordEncoder encoder;
    private final RoleRepo repo;
    
    public User toEntity(UserRegistrationRequestDTO x){
        var role = repo.findByName(RoleEnum.USER);
        var u = new User()
        .setEmail(x.email())
        .setFirstname(x.firstname())
        .setLastname(x.lastname())
        .setPassword(encoder.encode(x.password()))
        .setUsername(x.username())
        .setPhone(x.phone());
        role.ifPresent(u::setRole);
        return u;
    }
    
    public UserRegistrationResponseDTO toDTO(User u){
        var list = u.getOrders().stream().map(o -> o.getId()).collect(Collectors.toList());
        return new UserRegistrationResponseDTO(u.getId(),u.getUsername(),u.getFirstname(),u.getLastname(),u.getEmail(),u.getPhone(),u.getRole(),list,u.getVersion());
    }
    
    public UserResponse toDTOEmail(User u){
        return new UserResponse(u.getUsername(),u.getFirstname(),u.getLastname(),u.getEmail());
    }
}
