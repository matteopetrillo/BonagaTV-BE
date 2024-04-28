package it.petrillo.bonagatv.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return Base64.getEncoder().encodeToString(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(Base64.getEncoder().encodeToString(rawPassword.toString().getBytes()));
    }

    public String decode(String encodedPsw) {
        return new String(Base64.getDecoder().decode(encodedPsw.getBytes()), StandardCharsets.UTF_8);
    }
}
