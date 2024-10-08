/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author hp
 */
@Entity
@Table(name="cart_item", indexes = {
    @Index(name = "idx_session_id", columnList = "session_id")})
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="session_id",nullable=false)
    private UserShoppingSession session;
    
    @OneToOne
    @JoinColumn(name="product_id",nullable=false)
    private Product product;
    
    @Column(name="quantity")
    private Integer quantity;
}
