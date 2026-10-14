package com.pranit.productsupport.core.service;
import com.pranit.productsupport.contracts.CreatePaymentResponse;import com.pranit.productsupport.core.domain.Payment;import com.pranit.productsupport.core.payment.PaymentGateway;import com.pranit.productsupport.core.repository.PaymentRepository;import org.springframework.stereotype.Service;
@Service public class PaymentService {
 private final PaymentTransactionService tx;private final PaymentGateway gateway;private final PaymentRepository payments;
 public PaymentService(PaymentTransactionService t,PaymentGateway g,PaymentRepository p){tx=t;gateway=g;payments=p;}
 public CreatePaymentResponse create(Long quoteId,Long customerId){Payment p=tx.start(quoteId,customerId,gateway.providerName());if(p.getProviderOrderId()!=null)return dto(p);try{PaymentGateway.GatewayOrder order=gateway.createOrder(p.getAmountPaise(),p.getCurrency(),"psp-"+p.getId(),"Product support repair/inspection quote "+quoteId);p=tx.attachOrder(p.getId(),order.orderId());return dto(p);}catch(RuntimeException e){tx.markFailed(p.getId());throw e;}}
 public PaymentTransactionService transaction(){return tx;}
 private CreatePaymentResponse dto(Payment p){return new CreatePaymentResponse(p.getId(),p.getQuote().getId(),p.getProvider(),p.getProviderOrderId(),p.getAmountPaise(),p.getCurrency(),p.getStatus(),gateway.checkoutKeyId());}
}
