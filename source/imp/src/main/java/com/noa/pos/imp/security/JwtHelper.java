package com.noa.pos.imp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noa.pos.imp.util.UtilSecutity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.function.Function;

@Component
@Configuration
public class JwtHelper {

//    private IDomainSpec domainSpec;
    private SecurityProperties securityProperties;
    //requirement :
    public long JWT_TOKEN_VALIDITY_MILLISECONDS;
    private static final Long K_MILLISECONDS_IN_A_MINUTE = 60L * 1000L;
    private final String secret;


    @Autowired
    public JwtHelper(SecurityProperties securityProperties) {
        Security.addProvider(new BouncyCastleProvider());
//        this.domainSpec = domainSpec;
        this.securityProperties = securityProperties;
//        List<TDomainDto> domainProperties = this.domainSpec.findAllByDomainTbz("CONFIG");
        Long domainTimeSession = Long.getLong("5L", 5);
        String secret = "HBSDFHSDghfsdhsd22535.";
//        for (TDomainDto dProperty : domainProperties) {
//            switch (dProperty.getDomName()) {
//                case "TIME_SESSION": {
//                    domainTimeSession = Long.parseLong(dProperty.getDomValue());
//                    break;
//                }
//                case "SECRET_KEY": {
//                    secret = dProperty.getDomValue();
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
        if (domainTimeSession <= 0L) {
            domainTimeSession = 5L;
        }

        this.JWT_TOKEN_VALIDITY_MILLISECONDS = domainTimeSession * K_MILLISECONDS_IN_A_MINUTE;

        int frequency = Math.round(86 / (float) secret.length()) + 1;
        this.secret = secret.repeat(frequency);
        System.out.println(this.secret);
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_MILLISECONDS))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateAuthHeader(String authHeader) {
        return authHeader != null && authHeader.startsWith(this.securityProperties.getTOKEN_PREFIX());
    }

    public String getTokenFromAuthHeader(String authHeader) {
        return authHeader.substring(this.securityProperties.getTOKEN_PREFIX().length()).trim();
    }

    public String getSignedString(String json, String privateKeyStr, String password, String publicKeyStr) throws Exception {
        PrivateKey privkey = UtilSecutity.stringToPrivKeyPEMParser(privateKeyStr, password);
        X509Certificate cert = UtilSecutity.getX509Certificate(publicKeyStr);
        String thumbPrint =  this.calculateThumbprint(cert);

        return Jwts.builder()
                .setPayload(json)
                .setHeaderParam(JwsHeader.ALGORITHM, SignatureAlgorithm.RS256.getValue())
                .setHeaderParam(JwsHeader.X509_CERT_SHA1_THUMBPRINT, thumbPrint)
                .signWith(privkey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String getSerializedJson(String encryptedJson, String publicKeyStr) throws Exception {
        X509Certificate cert = UtilSecutity.getX509Certificate(publicKeyStr);
        cert.getSerialNumber();
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(Jwts.parserBuilder().setSigningKey(cert.getPublicKey()).build().parseClaimsJws(encryptedJson).getBody());
    }

    private String calculateThumbprint(X509Certificate cert) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] der = cert.getEncoded();
            md.update(der);
            byte[] digest = md.digest();
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
