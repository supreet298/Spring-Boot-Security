package com.supreet.security;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretKetTest {

    @Test
    public void generateSecretKey() {
        SecretKey Key = Jwts.SIG.HS512.key().build();
        String encodeKey = DatatypeConverter.printHexBinary(Key.getEncoded());
        System.out.printf("\nKey = [%s]\n", encodeKey);
    }

}
