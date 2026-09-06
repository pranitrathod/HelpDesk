package com.pranit.helpdesk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "helpdesk.payments")
public class PaymentGatewayProperties {
  private final Razorpay razorpay = new Razorpay();
  private final Juspay juspay = new Juspay();

  public Razorpay getRazorpay() {
    return razorpay;
  }
  public Juspay getJuspay() {
    return juspay;
  }

  public static class Razorpay {
    private String baseUrl = "https://api.razorpay.com";
    private String keyId;
    private String keySecret;
    public String getBaseUrl() {
      return baseUrl;
    }
    public void setBaseUrl(String value) {
      baseUrl = value;
    }
    public String getKeyId() {
      return keyId;
    }
    public void setKeyId(String value) {
      keyId = value;
    }
    public String getKeySecret() {
      return keySecret;
    }
    public void setKeySecret(String value) {
      keySecret = value;
    }
  }

  public static class Juspay {
    private String baseUrl = "https://api.juspay.in";
    private String merchantId;
    private String apiKey;
    public String getBaseUrl() {
      return baseUrl;
    }
    public void setBaseUrl(String value) {
      baseUrl = value;
    }
    public String getMerchantId() {
      return merchantId;
    }
    public void setMerchantId(String value) {
      merchantId = value;
    }
    public String getApiKey() {
      return apiKey;
    }
    public void setApiKey(String value) {
      apiKey = value;
    }
  }
}
