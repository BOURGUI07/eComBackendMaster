/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import main.models.User;
import main.util.PaymentProvider;
import main.util.PaymentType;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@Schema(title = "UserPaymentDTO", description = "Parameters required to create a user payment")
public record UserPaymentDTO(
        Integer id,
        @ValidId(message="Id must be not null, must by positive")
        Integer userId,
        @NotNull(message="payment type is required")
        PaymentType type,
        @NotNull(message="payment provider is required")
        PaymentProvider provider,
        @NotNull(message="account number is required")
        @Positive
        Integer accountNo,
        @NotNull(message="cart expiration date is required")
        LocalDate expiryDate
        ) {

}
