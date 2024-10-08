/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.CartItemDTO;
import main.exception.EntityNotFoundException;
import main.repo.CartItemRepo;
import main.repo.ProductRepo;
import main.util.mapper.CartItemMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import main.repo.UserShoppingSessionRepo;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
@Data
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class CartItemService {
      CartItemMapper mapper;
      CartItemRepo repo;
      UserShoppingSessionRepo sessionRepo;
      ProductRepo productRepo;
    @NonFinal Validator validator;
    
    @Transactional
    @CacheEvict(value={
        "allCartItems", "cartItemById"
    }, allEntries=true)
    public CartItemDTO update(Integer id, CartItemDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var o = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart Item with id: " + id + " isn't found"))
        .setQuantity(x.quantity());
        productRepo.findById(x.productId()).ifPresent(o::setProduct);
        sessionRepo.findById(x.sessionId()).ifPresent(o::setSession);
        var saved = repo.save(o);
        return mapper.toDTO(saved);
    }
    @Cacheable(value="cartItemById", key="#id", condition="#id!=null && #id>0",unless = "#result == null")
    public CartItemDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Cart Item with id: " + id + " isn't found"));
    }
    @Cacheable(value="allCartItems", key = "'findAll_' + #page + '_' + #size",unless="#result.isEmpty()")
    public Page<CartItemDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    @CacheEvict(value={
        "allCartItems", "cartItemById"
    }, allEntries=true)
    public CartItemDTO create(CartItemDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        
        var s = mapper.toEntity(x);
        var saved = repo.save(s);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allCartItems", "cartItemById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    @CacheEvict(value={
        "allCartItems", "cartItemById"
    }, allEntries=true)
    public void clearCache(){
        
    } 
}
