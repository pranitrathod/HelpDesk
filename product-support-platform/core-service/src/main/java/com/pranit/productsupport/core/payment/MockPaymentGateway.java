package com.pranit.productsupport.core.payment;
import java.util.UUID;import org.springframework.context.annotation.Profile;import org.springframework.stereotype.Component;
@Component @Profile("local") public class MockPaymentGateway implements PaymentGateway {public GatewayOrder createOrder(long amountPaise,String currency,String receipt,String description){return new GatewayOrder("order_mock_"+UUID.randomUUID());}public String providerName(){return "MOCK";}public String checkoutKeyId(){return "mock_key_for_local_only";}}
