package com.pranit.productsupport.core.payment;
public interface PaymentGateway { GatewayOrder createOrder(long amountPaise,String currency,String receipt,String description); String providerName(); String checkoutKeyId(); record GatewayOrder(String orderId){} }
