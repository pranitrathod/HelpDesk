package com.pranit.helpdesk.payment;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

final class PaymentSignatureVerifier {
  private PaymentSignatureVerifier() {}

  static boolean verifiesHmacSha256(String payload, String signature, String secret) {
    if (signature == null || secret == null || secret.isBlank())
      return false;
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
      byte[] expected = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
      byte[] supplied = hexToBytes(signature);
      return MessageDigest.isEqual(expected, supplied);
    } catch (Exception exception) {
      return false;
    }
  }

  private static byte[] hexToBytes(String value) {
    if (value.length() % 2 != 0)
      return new byte[0];
    byte[] bytes = new byte[value.length() / 2];
    for (int index = 0; index < value.length(); index += 2) {
      bytes[index / 2] = (byte) Integer.parseInt(value.substring(index, index + 2), 16);
    }
    return bytes;
  }
}
