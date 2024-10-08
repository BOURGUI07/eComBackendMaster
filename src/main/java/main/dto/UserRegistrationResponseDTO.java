/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;


import java.util.List;
import main.models.Role;

/**
 *
 * @author hp
 */
public record UserRegistrationResponseDTO(
     Integer id,

     String username,

     String firstname,

     String lastname,

     String email,

     String phone,

     Role role,
     
     List<Integer> orderIds,
     
     Integer version
        ) {

}
