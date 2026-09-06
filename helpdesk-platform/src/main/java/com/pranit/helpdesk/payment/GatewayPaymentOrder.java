package com.pranit.helpdesk.payment;

public class GatewayPaymentOrder {
  private final String providerOrderId;
  private final String checkoutToken;

  public GatewayPaymentOrder(String providerOrderId, String checkoutToken) {
    this.providerOrderId = providerOrderId;
    this.checkoutToken = checkoutToken;
  }

  public String getProviderOrderId() {
    return providerOrderId;
  }
  public String getCheckoutToken() {
    return checkoutToken;
  }
}
