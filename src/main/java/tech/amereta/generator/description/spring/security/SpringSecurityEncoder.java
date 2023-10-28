package tech.amereta.generator.description.spring.security;

import lombok.Getter;

@Getter
public enum SpringSecurityEncoder {

    BCRYPT("org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"),
    SCRYPT("org.springframework.security.crypto.scrypt.SCryptPasswordEncoder"),
    ARGON2("org.springframework.security.crypto.argon2.Argon2PasswordEncoder"),
    PBKDF2("org.springframework.security.crypto.password.Pbkdf2PasswordEncoder");

    final String className;

    SpringSecurityEncoder(String className) {
        this.className = className;
    }
}
