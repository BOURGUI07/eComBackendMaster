/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "ReviewsResponseDTO", description = "Parameters required to create a review")
public record ReviewsRequestDTO(
            @ValidId(message="Id must be not null, must by positive")
                    @Schema(title="userId",nullable=false)
            Integer userId,
            @ValidId(message="Id must be not null, must by positive")
                    @Schema(title="userId",nullable=false)
            Integer productId,
            @NotNull(message="rating is required")
            @Min(value=1, message="Rating must be at least 1")
            @Max(value=5, message="Rating must be at most 5")
                    @Schema(title="rating",nullable=false,minimum="1",maximum="5")
            Integer rating,
            @Schema(title="title",nullable=true)
            Optional<String> title,
            @Schema(title="title",nullable=true)
            Optional<String> content
        
        ) {

}
