/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "OrderItemCreationRequest", description = "Parameters required to create/update an order")
public record OrderItemCreationRequest(
        @ValidId(message="Id must be not null, must by positive")
        @Schema(title="orderId",nullable=false)
        Integer orderId,
        @ValidId(message="Id must be not null, must by positive")
        @Schema(title="productId",nullable=false)
        Integer productid,
        @NotNull
        @Positive
        @Schema(title="quantity",description="Ordered Product Qty",nullable=false)
        Integer quantity
        ) {

}
