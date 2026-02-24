package com.stakely.fluffybarnacle;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@SpringBootApplication
public class FluffybarnacleApplication {
  static void main(String[] args) {
    SpringApplication.run(FluffybarnacleApplication.class, args);
  }

  // TODO: Remove this once we have a real auth server
  @Bean
  CommandLineRunner run(String[] args) {
    return args1 -> {
      ClassPathResource resource = new ClassPathResource("authz.pem");
      InputStream inputStream = resource.getInputStream();
      String privateKeyPEM = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
      privateKeyPEM =
          privateKeyPEM
              .replace("-----BEGIN PRIVATE KEY-----", "")
              .replace("-----END PRIVATE KEY-----", "")
              .replaceAll("\\s", "");

      byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      RSAPrivateKey privateKey = (RSAPrivateKey) kf.generatePrivate(keySpec);

      JWTClaimsSet claims =
          new JWTClaimsSet.Builder()
              .subject("octavio")
              .issuer("self")
              .expirationTime(new Date(new Date().getTime() + 3600 * 1000))
              .claim("scope", "read")
              .build();

      SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claims);

      signedJWT.sign(new RSASSASigner(privateKey));

      System.out.println(signedJWT.serialize());
    };
  }
}
