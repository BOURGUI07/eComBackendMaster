/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import main.models.Product;
import main.models.UserShoppingSession;
import main.validation.EntityIdExists;
import main.validation.ValidQuantity;

/**
 *
 * @author hp
 */
@Schema(title = "CartItemDTO", description = "Parameters required to create/update a cart item")
@ValidQuantity(productIdField = "productId", quantityField = "quantity",
    message = "Quantity is required, Quantity must be positive, Requested quantity exceeds available inventory")
public record CartItemDTO(
        Integer id,
        @EntityIdExists(entityClass =UserShoppingSession.class,message="Id must be not null, must by positive, and must exists")
        Integer sessionId,
        @EntityIdExists(entityClass =Product.class,message="Id must be not null, must by positive, and must exists")
        Integer productId,
        Integer quantity
        ) {
}
