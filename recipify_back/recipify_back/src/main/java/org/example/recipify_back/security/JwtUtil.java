package org.example.recipify_back.security;

import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JwtUtil() throws Exception {
        this.privateKey = loadPrivateKey();
        this.publicKey = loadPublicKey();
        log.info("Loaded private and public keys successfully.");
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, username);
        log.info("Generated JWT for user: {}", username);  // Log lors de la génération d'un token
        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expire dans 10 heures
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String extractUsername(String token) {
        String username = extractClaim(token, Claims::getSubject);
        log.debug("Extracted username from JWT: {}", username);  // Log lors de l'extraction du username
        return username;
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraire tous les claims du token JWT en vérifiant avec la clé publique
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey) // Utiliser la clé publique pour valider
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Vérifier si le token est expiré
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        boolean isValid = (extractedUsername.equals(username) && !isTokenExpired(token));
        log.info("JWT validation for user {}: {}", username, isValid ? "valid" : "invalid");  // Log la validation
        return isValid;
    }
    // Charger la clé privée à partir d'un fichier PEM dans le classpath
    private PrivateKey loadPrivateKey() throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("private_key.pem")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Private key file not found");
            }
            String key = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String privateKeyPEM = key
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return keyFactory.generatePrivate(keySpec);
        }
    }


    // Charger la clé publique à partir d'un fichier PEM dans le classpath
    private PublicKey loadPublicKey() throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("public_key.pem")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Public key file not found");
            }
            String key = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String publicKeyPEM = key
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return keyFactory.generatePublic(keySpec);
        }
    }
}
