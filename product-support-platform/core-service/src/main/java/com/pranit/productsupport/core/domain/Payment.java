package com.pranit.productsupport.core.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="payments",uniqueConstraints=@UniqueConstraint(name="uk_payment_provider_order",columnNames={"provider","provider_order_id"})) public class Payment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="quote_id") private ServiceQuote quote;
 @Column(nullable=false,length=30) private String provider;
 @Column(name="provider_order_id",length=100) private String providerOrderId;
 @Column(name="provider_payment_id",length=100) private String providerPaymentId;
 @Column(name="amount_paise",nullable=false) private long amountPaise;
 @Column(nullable=false,length=3) private String currency;
 @Column(nullable=false,length=20) private String status;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 protected Payment(){} public Payment(ServiceQuote quote,String provider){this.quote=quote;this.provider=provider;this.amountPaise=quote.getAmountPaise();this.currency=quote.getCurrency();this.status="INITIATING";this.createdAt=Instant.now();this.updatedAt=this.createdAt;}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();if(updatedAt==null)updatedAt=createdAt;} @PreUpdate void preUpdate(){updatedAt=Instant.now();}
 public Long getId(){return id;} public ServiceQuote getQuote(){return quote;} public String getProvider(){return provider;} public String getProviderOrderId(){return providerOrderId;} public String getProviderPaymentId(){return providerPaymentId;} public long getAmountPaise(){return amountPaise;} public String getCurrency(){return currency;} public String getStatus(){return status;} public void attachOrder(String orderId){this.providerOrderId=orderId;this.status="PENDING";} public void fail(){if(!"CAPTURED".equals(status))status="FAILED";} public void capture(String paymentId){this.providerPaymentId=paymentId;this.status="CAPTURED";} public void markDuplicateCapture(String paymentId){this.providerPaymentId=paymentId;this.status="DUPLICATE_CAPTURE";}
}
