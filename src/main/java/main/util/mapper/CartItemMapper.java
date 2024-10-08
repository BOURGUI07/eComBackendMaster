/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.CartItemDTO;
import main.models.CartItem;
import main.repo.ProductRepo;
import org.springframework.stereotype.Service;
import main.repo.UserShoppingSessionRepo;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class CartItemMapper {
      UserShoppingSessionRepo sessionRepo;
      ProductRepo productRepo;
    
    public CartItem toEntity(CartItemDTO x){
        var c = new CartItem().setQuantity(x.quantity());
        sessionRepo.findById(x.sessionId()).ifPresent(c::setSession);
        productRepo.findById(x.productId()).ifPresent(c::setProduct);
        return c;
    }
    
    public CartItemDTO toDTO(CartItem c){
        return (c.getProduct()!=null && c.getSession()!=null) ?
                new CartItemDTO(c.getId(),c.getSession().getId(),c.getProduct().getId(),c.getQuantity()) : null;
    }
}
