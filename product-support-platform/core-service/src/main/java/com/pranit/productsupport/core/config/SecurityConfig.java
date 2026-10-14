package com.pranit.productsupport.core.config;
import javax.crypto.SecretKey; import javax.crypto.spec.SecretKeySpec; import java.nio.charset.StandardCharsets; import java.util.List;
import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import org.springframework.security.config.Customizer; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.oauth2.jose.jws.MacAlgorithm; import org.springframework.security.oauth2.jwt.JwtDecoder; import org.springframework.security.oauth2.jwt.JwtEncoder; import org.springframework.security.oauth2.jwt.NimbusJwtDecoder; import org.springframework.security.oauth2.jwt.JwtValidators; import org.springframework.security.oauth2.jwt.NimbusJwtEncoder; import org.springframework.security.oauth2.jwt.Jwt; import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.web.SecurityFilterChain; import com.nimbusds.jose.jwk.source.ImmutableSecret;
@Configuration @EnableMethodSecurity
public class SecurityConfig {
 @Bean SecretKey jwtSecretKey(@Value("${app.security.jwt-secret}") String value) { byte[] bytes=value.getBytes(StandardCharsets.UTF_8); if(bytes.length < 32) throw new IllegalStateException("JWT_SECRET must be at least 32 bytes"); return new SecretKeySpec(bytes,"HmacSHA256"); }
 @Bean JwtDecoder jwtDecoder(SecretKey key,@Value("${app.security.jwt-issuer}") String issuer) { NimbusJwtDecoder d=NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build(); d.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuer)); return d; }
 @Bean JwtEncoder jwtEncoder(SecretKey key) { return new NimbusJwtEncoder(new ImmutableSecret<>(key)); }
 @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  http.csrf(csrf->csrf.disable()).authorizeHttpRequests(a->a.requestMatchers("/internal/v1/auth/**","/internal/v1/webhooks/razorpay","/actuator/health/**","/actuator/info").permitAll().anyRequest().authenticated()).oauth2ResourceServer(o->o.jwt(j->j.jwtAuthenticationConverter(jwtAuthenticationConverter())));
  return http.build();
 }
 @Bean JwtAuthenticationConverter jwtAuthenticationConverter() { JwtAuthenticationConverter c=new JwtAuthenticationConverter(); c.setJwtGrantedAuthoritiesConverter(jwt -> { List<String> roles=jwt.getClaimAsStringList("roles"); return roles==null?List.of():roles.stream().map(SimpleGrantedAuthority::new).toList(); }); return c; }
}
