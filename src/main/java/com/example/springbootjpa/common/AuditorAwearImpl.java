package com.example.springbootjpa.common;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwearImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Anil");
    }
}
