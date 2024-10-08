/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Optional;
import main.util.WishListVisibility;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "WishListCreationRequest", description = "Parameters required to create a wishList")
public record WishListCreationRequest(
        @Size(min=3,max=100,message="wish list name must be between 3 and 100 characters")
        @NotBlank(message="wish list name is required")
                @Schema(title="name",description="wishlist name",nullable=false)
        String name,
        @Schema(title="desc",nullable=true,description="wishlist description")
        @Size(max=500,message="wishlist desc must be at max 500 characters")
        Optional<String> desc,
        @NotNull(message="wishlist visibility is required")
                @Schema(title="visibility",nullable=false,description="wishlist visibility",allowableValues = {"PRIVATE", "PUBLIC"})
        WishListVisibility visibility,
        @ValidId(message="Id must be not null, must by positive")
                @Schema(title="userId",nullable=false)
        Integer userId
        ) {
}
