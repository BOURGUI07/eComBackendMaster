/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.PaymentDetailDTO;
import main.dto.PaymentDetailResponseDTO;
import main.models.PaymentDetail;
import main.repo.OrderRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class PaymentDetailMapper {
      OrderRepo orepo;
    
    public PaymentDetail toEntity(PaymentDetailDTO x){
        var d = new PaymentDetail()
        .setPaymentProvider(x.provider())
        .setPaymentStatus(x.status());
        orepo.findById(x.orderId()).ifPresent(d::setOrder);
        return d;
    }
    
    public PaymentDetailResponseDTO toDTO(PaymentDetail d){
        var order = d.getOrder();
        return order!=null ? new PaymentDetailResponseDTO(d.getId(),order.getId(),d.getAmount(),
        d.getPaymentProvider(),d.getPaymentStatus(),d.getVersion()):null;
    }
}
