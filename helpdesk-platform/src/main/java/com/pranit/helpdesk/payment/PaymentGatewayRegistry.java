package com.pranit.helpdesk.payment;

import com.pranit.helpdesk.domain.PaymentGateway;
import com.pranit.helpdesk.exception.ResourceNotFoundException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayRegistry {
  private final Map<PaymentGateway, PaymentGatewayClient> clients =
      new EnumMap<>(PaymentGateway.class);

  public PaymentGatewayRegistry(List<PaymentGatewayClient> gatewayClients) {
    gatewayClients.forEach(client -> clients.put(client.gateway(), client));
  }

  public PaymentGatewayClient get(PaymentGateway gateway) {
    PaymentGatewayClient client = clients.get(gateway);
    if (client == null) {
      throw new ResourceNotFoundException("Payment gateway", gateway.name());
    }
    return client;
  }
}
